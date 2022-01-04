package task.simulation;

import Enums.TargetRunStatus;
import Enums.TargetStatus;
import Enums.TasksName;
import dtoObjects.TaskSummeryDTO;
import engineManager.EngineManager;
import exceptions.TaskException;
import graph.Graph;
import graph.SerialSet;
import target.Target;
import task.Task;
import task.TaskManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SimulationTaskManager extends TaskManager {

    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;


    //create taskManager for simulation task
    public SimulationTaskManager(Graph graph, int timePerTarget, double chancePerTarget, double chanceWarning,
                                 boolean isRandom, boolean fromScratch, Consumer<String> consumer, int maxParallel) throws TaskException{
        if(fromScratch && EngineManager.graphStatic != null){
            EngineManager.graphStatic = new Graph(graph);
            for (SerialSet serialSet : EngineManager.graphStatic.getSerialSetMap().values()){
                for (Target target : serialSet.getAllSet().values()){
                    target.addSerialSet(serialSet);
                }
            }
        }else if(EngineManager.graphStatic == null && !fromScratch ){
            consumer.accept("Simulation has not run yet on the current graph so it will run from scratch.");
            EngineManager.graphStatic = new Graph(graph);
        }
        else if(fromScratch && EngineManager.graphStatic == null){
            EngineManager.graphStatic = new Graph(graph);
        }
        else{
            if(EngineManager.graphStatic.getGraphMap().size() == 0){
                consumer.accept("The graph is empty, you can choose to run again from scratch.");
            }
        }
        this.summeryDTO = new TaskSummeryDTO();
        this.maxParallel = maxParallel;
        this.skipped = new HashSet<>();
        this.failed = new HashSet<>();
        this.succeed = new HashSet<>();
        this.warnings = new HashSet<>();
        this.pool = Executors.newFixedThreadPool(maxParallel);
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
        handleRunSimulation(timePerTarget, chancePerTarget, chanceWarning, isRandom, consumer);
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

    private void handleRunSimulation(int timePerTarget, double chancePerTarget, double chanceWarning,
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
        if(target.getRunStatus() == TargetRunStatus.WARNING){
            addToWarnings(target);
        }else {
            addToSucceed(target);
        }
        System.out.println("Up counter from:"+this.counter + "becuase success target:" + target.getName());
        upCounter();
        Set<Target> targetsReq = target.getRequiredForList();
        for (Target target1 : targetsReq) {
            EngineManager.graphStatic.removeConnection(target1, target);
            if (EngineManager.graphStatic.isRunnable(target1) && !(target1.getStatus().equals(TargetRunStatus.SKIPPED))) {
                task.writeToConsumers(consumerList, "Target " + target1.getName() + " turned WAITING.");
                target1.setStatus(TargetStatus.WAITING);
                target1.moveToWaitingInAllSerials();
                if(target1.isCanRunSerial()){
                    pool.execute(getSimulationTask(processTime,chanceSuccess,chanceWarning,isRandom,consumerList.get(0),target1));
                }
            }
        }
        EngineManager.graphStatic.removeFromGraph(target);
        for (SerialSet serialSet : target.getSerialSetMap().values()){
            serialSet.setRun(false);
            Target target1 = serialSet.handleFinish();
            if(target1 != null){
                addToPool(getSimulationTask(processTime,chanceSuccess,chanceWarning,isRandom,consumerList.get(0),target1));
            }

        }

    }

    public synchronized void addToPool(Task task){
//        System.out.println(task.getTargetName());
        this.pool.execute(task);
    }

    public synchronized void handleFail(Target failedTarget, List<Consumer<String>> consumersList, Task task){
        addToFailed(failedTarget);
        for (SerialSet serialSet : failedTarget.getSerialSetMap().values()){
            Target target = serialSet.handleFinish();
            if(target != null)
                addToPool(getSimulationTask(processTime,chanceSuccess,chanceWarning,isRandom,consumersList.get(0),target));
        }
        System.out.println("Up counter from:"+this.counter + "becuase fail target:" + failedTarget.getName());
        upCounter();
        for (Target target : failedTarget.getRequiredForList()){
            handleFailureRec(target, consumersList, task, failedTarget);
        }
    }

}


