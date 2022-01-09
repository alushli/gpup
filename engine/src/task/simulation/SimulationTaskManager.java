package task.simulation;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import Enums.TargetStatus;
import Enums.TasksName;
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

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class SimulationTaskManager extends TaskManager {

    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;


    //create taskManager for simulation task
    public SimulationTaskManager(Graph graph, int timePerTarget, double chancePerTarget, double chanceWarning,
                                 boolean isRandom, boolean fromScratch, Consumer<String> consumer, int maxParallel, Boolean synchroObj) throws TaskException{
        this.synchroObj = synchroObj;

        Collection<Target> targetToRemove = new HashSet<>();

        if(fromScratch && EngineManager.graphStatic != null){
            EngineManager.graphStatic = graph;
            for (SerialSet serialSet : EngineManager.graphStatic.getSerialSetMap().values()){
                for (Target target : serialSet.getAllSet().values()){
                    target.addSerialSet(serialSet);
                }
            }

        }else if(EngineManager.graphStatic == null && !fromScratch ){
            consumer.accept("Simulation has not run yet on the current graph so it will run from scratch.");
            EngineManager.graphStatic = graph;
        }
        else if(fromScratch && EngineManager.graphStatic == null){
            EngineManager.graphStatic = graph;
        }
        else{
            for (Target target : graph.getGraphMap().keySet()){
                if(target.getRunStatus().equals(TargetRunStatus.NONE)){
                    consumer.accept("Target "+ target.getName() + " didnt include at the last run - so it run from scratch on it.\n");
                }else if((target.getRunStatus().equals(TargetRunStatus.SUCCESS) ||(target.getRunStatus().equals(TargetRunStatus.WARNING)))){
                    targetToRemove.add(target);
                }
            }
            for (Target target : targetToRemove){
                graph.removeFromGraph(target);
            }
            EngineManager.graphStatic = graph;
            if(EngineManager.graphStatic.getGraphMap().size() == 0){
                consumer.accept("The graph is empty, you can choose to run again from scratch.\n");
            }
        }
        this.summeryDTO = new TaskSummeryDTO();
        this.maxParallel = maxParallel;
        this.skipped = new HashSet<>();
        this.failed = new HashSet<>();
        this.succeed = new HashSet<>();
        this.warnings = new HashSet<>();
        this.pool = new ThreadPoolExecutor(maxParallel, maxParallel,50, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        this.processTime = timePerTarget;
        this.chanceSuccess = chancePerTarget;
        this.chanceWarning = chanceWarning;
        this.isRandom = isRandom;
        this.initSize = EngineManager.graphStatic.getGraphMap().size();
        for (Target target: EngineManager.graphStatic.getGraphMap().keySet()){
            target.setStatus(TargetStatus.FROZEN);
            target.setRunStatus(TargetRunStatus.NONE);
        }
        this.folderPath =saveSimulationFolder();
       for (Target target : EngineManager.graphStatic.getGraphMap().keySet()){
           for (SerialSet serialSet : target.getSerialSetMap().values()){
               serialSet.init();
           }
       }

       this.taskRuntimeDTO = new TaskRuntimeDTO(graph.getGraphMap().keySet());

        if(!targetToRemove.isEmpty())
            removePassTargetsFromDependsOn(this.taskRuntimeDTO, targetToRemove);

        removeFromReqAndDep();

       this.taskRuntimeDTO.updatePositions();
       this.isPausedObj = new Boolean(false);
       this.isPaused = false;


    }


    private void removeFromReqAndDep(){
        Set<String> targetToRemove = new HashSet<>();
        for (TargetRuntimeDTO targetRuntimeDTO : this.taskRuntimeDTO.getMap().values()){
            for (String targetName : targetRuntimeDTO.getRequiredFor()){
                if(EngineManager.graphStatic.getTargetByName(targetName) == null){
                    targetToRemove.add(targetName);
                }
            }
            for (String targetName : targetRuntimeDTO.getDependsOn()){
                if(EngineManager.graphStatic.getTargetByName(targetName) == null){
                    targetToRemove.add(targetName);
                }
            }
        }

        for (String target : targetToRemove){
            for(TargetRuntimeDTO targetRuntimeDTO :this.taskRuntimeDTO.getMap().values()){
                targetRuntimeDTO.getRequiredFor().remove(target);
                targetRuntimeDTO.getDependsOn().remove(target);
            }
        }
    }

    private void removePassTargetsFromDependsOn(TaskRuntimeDTO taskRuntimeDTO,  Collection<Target> targetToRemove){
        for(TargetRuntimeDTO targetRuntimeDTO : taskRuntimeDTO.getMap().values()){
            for(Target target:targetToRemove){
                if(targetRuntimeDTO.getDependsOn().contains(target.getName())) {
                    targetRuntimeDTO.getDependsOn().remove(target.getName());
                    targetRuntimeDTO.getRequiredFor().remove((target.getName()));
                }
            }
        }
    }

    public void setTaskRunTime(TaskRuntimeDTO taskRuntimeDTO){
        this.taskRuntimeDTO = taskRuntimeDTO;
    }


    private List<Target> filterRunnableBySerialSets(List<Target> targets){
        List<Target> filtered = new ArrayList<>();
        for (Target target : targets){
            if(target.isCanRunSerial()){
                for (SerialSet serialSet : target.getSerialSetMap().values()){
                    serialSet.setRun(true);
                    serialSet.setInProccess(target);
                    target.setStatus(TargetStatus.WAITING);
                }
                filtered.add(target);
            }
        }
        for (Target target: targets){
            if(target.getStatus().equals(TargetStatus.FROZEN)){
                target.moveToWaitingInAllSerials();
            }
        }
        return filtered;
    }

    public void handleRunSimulation(int timePerTarget, double chancePerTarget, double chanceWarning,
                                     boolean isRandom, Consumer<String> consumer) {
        long startTime = System.currentTimeMillis();
        List<Target> runnableList = EngineManager.graphStatic.getRunnableTargets();
        runnableList = filterRunnableBySerialSets(runnableList);

        addToPool(runnableList, timePerTarget, chancePerTarget, chanceWarning, isRandom, consumer);
        synchronized (this){
            try {
                while (this.counter < this.initSize){
                    this.wait();
                }
                this.pool.shutdown();
                this.pool = null;
                consumer.accept("\nSimulation done!");
                long endTime = System.currentTimeMillis();
                summeryDTO.setHMS(Task.convertMillisToHMS(endTime-startTime));
            }catch (Exception e){
                System.out.println("Error in handleRunSimulation() - Task manager");
            }
        }
    }

    private synchronized void addToPool( List<Target> runnableList, int timePerTarget, double chancePerTarget, double chanceWarning, boolean isRandom,
                                        Consumer<String> consumer) {
        for (Target target : runnableList) {
            synchronized (this.synchroObj){
                this.taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
            }
            this.pool.execute(getSimulationTask(timePerTarget, chancePerTarget, chanceWarning, isRandom, consumer, target));
        }
    }


    private SimulationTask getSimulationTask(int timePerTarget, double chancePerTarget, double chanceWarning, boolean isRandom,
                                             Consumer<String> consumer, Target target) {
        try {
            return new SimulationTask(EngineManager.graphStatic, timePerTarget, chancePerTarget, chanceWarning, isRandom, consumer, target, this);
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized void handleSucceed(Target target, List<Consumer<String>> consumerList, Task task){
        synchronized (this.synchroObj){
            if(target.getRunStatus() == TargetRunStatus.WARNING){
                taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.FINISHED);
                taskRuntimeDTO.upFinish();
                taskRuntimeDTO.getTargetByName(target.getName()).setFinishStatus(TargetRunStatus.WARNING);
                addToWarnings(target);
            }else {
                taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.FINISHED);
                taskRuntimeDTO.upFinish();
                taskRuntimeDTO.getTargetByName(target.getName()).setFinishStatus(TargetRunStatus.SUCCESS);
                addToSucceed(target);
            }
        }
        System.out.println("Up counter from:"+this.counter + "because success target:" + target.getName());
        upCounter();
        Set<Target> targetsReq = target.getRequiredForList();
        for (Target target1 : targetsReq) {
            if(EngineManager.graphStatic.getGraphMap().containsKey(target1)){
                EngineManager.graphStatic.removeConnection(target1, target);
                synchronized (this.synchroObj) {
                    this.taskRuntimeDTO.getTargetByName(target1.getName()).getDependsOn().remove(target.getName());
                }
                if (EngineManager.graphStatic.isRunnable(target1) && !(target1.getStatus().equals(TargetRunStatus.SKIPPED))) {
                    task.writeToConsumers(consumerList, "Target " + target1.getName() + " turned WAITING.");
                    target1.setStatus(TargetStatus.WAITING);
                    target1.moveToWaitingInAllSerials();
                    synchronized (this.synchroObj){
                        taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
                    }
                    if(target1.isCanRunSerial()){
                        pool.execute(getSimulationTask(processTime,chanceSuccess,chanceWarning,isRandom,consumerList.get(0),target1));
                    }
                }
            }

        }
        EngineManager.graphStatic.removeFromGraph(target);
        for (SerialSet serialSet : target.getSerialSetMap().values()){
            serialSet.setRun(false);
            Target target1 = serialSet.handleFinish();
            synchronized (this.synchroObj){
                taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
            }
            if(target1 != null){
                addToPool(getSimulationTask(processTime,chanceSuccess,chanceWarning,isRandom,consumerList.get(0),target1));
            }

        }

    }



    public synchronized void addToPool(Task task){
        synchronized (this.synchroObj){
            taskRuntimeDTO.getTargetByName(task.getTargetName()).setStatus(TargetRuntimeStatus.WAITING);
        }
          this.pool.execute(task);
    }

    public synchronized void handleFail(Target failedTarget, List<Consumer<String>> consumersList, Task task){
            synchronized (this.synchroObj){
            taskRuntimeDTO.getTargetByName(failedTarget.getName()).setStatus(TargetRuntimeStatus.FINISHED);
            taskRuntimeDTO.upFinish();
            taskRuntimeDTO.getTargetByName(failedTarget.getName()).setFinishStatus(TargetRunStatus.FAILURE);
        }

        addToFailed(failedTarget);
        for (SerialSet serialSet : failedTarget.getSerialSetMap().values()){
            Target target = serialSet.handleFinish();
            if(target != null){
                synchronized (this.synchroObj){
                    taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.WAITING);
                }
                addToPool(getSimulationTask(processTime,chanceSuccess,chanceWarning,isRandom,consumersList.get(0),target));
            }

        }
        System.out.println("Up counter from:"+this.counter + "becuase fail target:" + failedTarget.getName());
        upCounter();
        for (Target target : failedTarget.getRequiredForList()){
            if(EngineManager.graphStatic.getGraphMap().containsKey(target)){
                handleFailureRec(target, consumersList, task, failedTarget);
            }
        }
    }

}


