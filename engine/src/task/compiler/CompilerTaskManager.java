package task.compiler;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import Enums.TargetStatus;
import dtoObjects.TargetRuntimeDTO;
import dtoObjects.TaskRuntimeDTO;
import dtoObjects.TaskSummeryDTO;
import engineManager.EngineManager;
import exceptions.TaskException;
import graph.Graph;
import graph.SerialSet;
import target.Target;
import task.Task;
import task.TaskManager;
import task.simulation.SimulationTask;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CompilerTaskManager extends TaskManager {
    private String sourceFolder;
    private String productFolder;

    public CompilerTaskManager(Graph graph, String sourceFolder, String productFolder, boolean fromScratch,
                               Consumer<String> consumer, int maxParallel, Boolean synchroObj) throws TaskException {
        this.synchroObj = synchroObj;

        Collection<Target> targetToRemove = new HashSet<>();

        if (fromScratch && EngineManager.graphStatic != null) {
            EngineManager.graphStatic = graph;
            for (SerialSet serialSet : EngineManager.graphStatic.getSerialSetMap().values()) {
                for (Target target : serialSet.getAllSet().values()) {
                    target.addSerialSet(serialSet);
                }
            }

        } else if (EngineManager.graphStatic == null && !fromScratch) {
            consumer.accept("Compiler task has not run yet on the current graph so it will run from scratch.");
            EngineManager.graphStatic = graph;
        } else if (fromScratch && EngineManager.graphStatic == null) {
            EngineManager.graphStatic = graph;
        } else {
            for (Target target : graph.getGraphMap().keySet()) {
                if (target.getRunStatus().equals(TargetRunStatus.NONE)) {
                    consumer.accept("Target " + target.getName() + " didnt include at the last run - so it run from scratch on it.\n");
                } else if ((target.getRunStatus().equals(TargetRunStatus.SUCCESS))) {
                    targetToRemove.add(target);
                }
            }
            for (Target target : targetToRemove) {
                graph.removeFromGraph(target);
            }
            EngineManager.graphStatic = graph;
            if (EngineManager.graphStatic.getGraphMap().size() == 0) {
                consumer.accept("The graph is empty, you can choose to run again from scratch.\n");
            }
        }
        this.summeryDTO = new TaskSummeryDTO();
        this.maxParallel = maxParallel;
        this.skipped = new HashSet<>();
        this.failed = new HashSet<>();
        this.succeed = new HashSet<>();
        this.pool = new ThreadPoolExecutor(maxParallel, maxParallel,50, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
        this.initSize = EngineManager.graphStatic.getGraphMap().size();
        for (Target target : EngineManager.graphStatic.getGraphMap().keySet()) {
            target.setStatus(TargetStatus.FROZEN);
            target.setRunStatus(TargetRunStatus.NONE);
        }
        this.folderPath = saveCompilerFolder();
        for (Target target : EngineManager.graphStatic.getGraphMap().keySet()) {
            for (SerialSet serialSet : target.getSerialSetMap().values()) {
                serialSet.init();
            }
        }

        this.taskRuntimeDTO = new TaskRuntimeDTO(graph.getGraphMap().keySet());

        if (!targetToRemove.isEmpty())
            removePassTargetsFromDependsOn(this.taskRuntimeDTO, targetToRemove);

        removeFromReqAndDep();

        this.taskRuntimeDTO.updatePositions();
        this.isPausedObj = new Boolean(false);
        this.isPaused = false;

    }


    private void removeFromReqAndDep() {
        Set<String> targetToRemove = new HashSet<>();
        for (TargetRuntimeDTO targetRuntimeDTO : this.taskRuntimeDTO.getMap().values()) {
            for (String targetName : targetRuntimeDTO.getRequiredFor()) {
                if (EngineManager.graphStatic.getTargetByName(targetName) == null) {
                    targetToRemove.add(targetName);
                }
            }
            for (String targetName : targetRuntimeDTO.getDependsOn()) {
                if (EngineManager.graphStatic.getTargetByName(targetName) == null) {
                    targetToRemove.add(targetName);
                }
            }
        }

        for (String target : targetToRemove) {
            for (TargetRuntimeDTO targetRuntimeDTO : this.taskRuntimeDTO.getMap().values()) {
                targetRuntimeDTO.getRequiredFor().remove(target);
                targetRuntimeDTO.getDependsOn().remove(target);
            }
        }
    }

    private void removePassTargetsFromDependsOn(TaskRuntimeDTO taskRuntimeDTO, Collection<Target> targetToRemove) {
        for (TargetRuntimeDTO targetRuntimeDTO : taskRuntimeDTO.getMap().values()) {
            for (Target target : targetToRemove) {
                if (targetRuntimeDTO.getDependsOn().contains(target.getName())) {
                    targetRuntimeDTO.getDependsOn().remove(target.getName());
                    targetRuntimeDTO.getRequiredFor().remove((target.getName()));
                }
            }
        }
    }

    public void setTaskRunTime(TaskRuntimeDTO taskRuntimeDTO) {
        this.taskRuntimeDTO = taskRuntimeDTO;
    }


    private List<Target> filterRunnableBySerialSets(List<Target> targets) {
        List<Target> filtered = new ArrayList<>();
        for (Target target : targets) {
            if (target.isCanRunSerial()) {
                for (SerialSet serialSet : target.getSerialSetMap().values()) {
                    serialSet.setRun(true);
                    serialSet.setInProccess(target);
                    target.setStatus(TargetStatus.WAITING);
                }
                filtered.add(target);
            }
        }
        for (Target target : targets) {
            if (target.getStatus().equals(TargetStatus.FROZEN)) {
                target.moveToWaitingInAllSerials();
            }
        }
        return filtered;
    }

    public void handleRunCompiler(String sourceFolder, String productFolder, Consumer<String> consumer) {
        long startTime = System.currentTimeMillis();
        List<Target> runnableList = EngineManager.graphStatic.getRunnableTargets();
        runnableList = filterRunnableBySerialSets(runnableList);

        addToPool(runnableList, sourceFolder, productFolder, consumer);
        synchronized (this) {
            try {
                while (this.counter < this.initSize) {
                    this.wait();
                }
                this.pool.shutdown();
                this.pool = null;
                consumer.accept("\nCompiler task done!");
                long endTime = System.currentTimeMillis();
                summeryDTO.setHMS(Task.convertMillisToHMS(endTime - startTime));
            } catch (Exception e) {
                System.out.println("Error in handleRunCompiler() - Task manager");
            }
        }
    }

    private synchronized void addToPool(List<Target> runnableList,String sourceFolder,String productFolder, Consumer<String> consumer) {
        for (Target target : runnableList) {
            synchronized (this.synchroObj) {
                this.taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
            }
            this.pool.execute(getCompilerTask(sourceFolder, productFolder, consumer, target));
        }
    }


    private CompilerTask getCompilerTask(String sourceFolder, String productFolder, Consumer<String> consumer, Target target) {
        try {
            return new CompilerTask(EngineManager.graphStatic, sourceFolder, productFolder, consumer, target, this);
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized void handleSucceed(Target target, List<Consumer<String>> consumerList, Task task) {
        synchronized (this.synchroObj) {
                taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.FINISHED);
                taskRuntimeDTO.upFinish();
                taskRuntimeDTO.getTargetByName(target.getName()).setFinishStatus(TargetRunStatus.SUCCESS);
                addToSucceed(target);
        }
        upCounter();
        Set<Target> targetsReq = target.getRequiredForList();
        for (Target target1 : targetsReq) {
            if (EngineManager.graphStatic.getGraphMap().containsKey(target1)) {
                EngineManager.graphStatic.removeConnection(target1, target);
                synchronized (this.synchroObj) {
                    this.taskRuntimeDTO.getTargetByName(target1.getName()).getDependsOn().remove(target.getName());
                }
                if (EngineManager.graphStatic.isRunnable(target1) && !(target1.getStatus().equals(TargetRunStatus.SKIPPED))) {
                    task.writeToConsumers(consumerList, "Target " + target1.getName() + " turned WAITING.");
                    target1.setStatus(TargetStatus.WAITING);
                    target1.moveToWaitingInAllSerials();
                    synchronized (this.synchroObj) {
                        taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
                    }
                    if (target1.isCanRunSerial()) {
                        pool.execute(getCompilerTask(sourceFolder, productFolder, consumerList.get(0), target1));
                    }
                }
            }

        }
        EngineManager.graphStatic.removeFromGraph(target);
        for (SerialSet serialSet : target.getSerialSetMap().values()) {
            serialSet.setRun(false);
            Target target1 = serialSet.handleFinish();
            synchronized (this.synchroObj) {
                taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
            }
            if (target1 != null) {
                addToPool(getCompilerTask(sourceFolder, productFolder, consumerList.get(0), target1));
            }
        }
    }

    public synchronized void addToPool(Task task) {
        synchronized (this.synchroObj) {
            taskRuntimeDTO.getTargetByName(task.getTargetName()).setStatus(TargetRuntimeStatus.WAITING);
        }
        this.pool.execute(task);
    }

    public synchronized void handleFail(Target failedTarget, List<Consumer<String>> consumersList, Task task) {
        synchronized (this.synchroObj) {
            taskRuntimeDTO.getTargetByName(failedTarget.getName()).setStatus(TargetRuntimeStatus.FINISHED);
            taskRuntimeDTO.upFinish();
            taskRuntimeDTO.getTargetByName(failedTarget.getName()).setFinishStatus(TargetRunStatus.FAILURE);
        }

        addToFailed(failedTarget);
        for (SerialSet serialSet : failedTarget.getSerialSetMap().values()) {
            Target target = serialSet.handleFinish();
            if (target != null) {
                synchronized (this.synchroObj) {
                    taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
                }
                addToPool(getCompilerTask(sourceFolder, productFolder, consumersList.get(0), target));
            }

        }
        upCounter();
        for (Target target : failedTarget.getRequiredForList()) {
            if (EngineManager.graphStatic.getGraphMap().containsKey(target)) {
                handleFailureRec(target, consumersList, task, failedTarget);
            }
        }
    }
}
