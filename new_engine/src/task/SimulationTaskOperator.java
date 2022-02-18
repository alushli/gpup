package task;

import User.Admin;
import dtoObjects.NewCompilationTaskDetails;
import dtoObjects.NewSimulationTaskDetails;
import graph.Graph;
import newEnums.TargetAllStatuses;
import newEnums.TaskStatus;
import newEnums.TasksName;
import target.Target;

import java.util.HashMap;

public class SimulationTaskOperator extends TaskOperator{
    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;

    public SimulationTaskOperator(Graph graph, String name, String admin, int pricePerTarget, int processTime, boolean isRandom, double chanceSuccess, double chanceWarning){
        this.origin = graph;
        this.workGraph = new Graph(origin);
        this.admin = admin;
        this.name = name;
        this.workerMap = new HashMap<>();
        this.pricePerTarget = pricePerTarget;
        this.tasksName = tasksName;
        this.success = new HashMap<>();
        this.failed = new HashMap<>();
        this.waiting = new HashMap<>();
        this.skipped = new HashMap<>();
        this.frozen = new HashMap<>();
        this.targetStatusMap = new HashMap<>();
        for (Target target : origin.getGraphMap().keySet()){
            targetStatusMap.put(target.getName(), TargetAllStatuses.FROZEN);
            frozen.put(target.getName(), target);
        }
        this.processTime = processTime;
        this.isRandom = isRandom;
        this.chanceSuccess = chanceSuccess;
        this.chanceWarning = chanceWarning;
        this.taskStatus = TaskStatus.FROZEN;
    }

    public int getProcessTime() {
        return processTime;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public double getChanceSuccess() {
        return chanceSuccess;
    }

    public double getChanceWarning() {
        return chanceWarning;
    }

    public SimulationTaskOperator(NewSimulationTaskDetails simulationTaskDetails, String admin, Graph originGraph){
        this.name = simulationTaskDetails.getTaskName();
        this.origin = new Graph(simulationTaskDetails.getTargets(),originGraph );
        this.workGraph = new Graph(origin);
        this.admin = admin;
        this.workerMap = new HashMap<>();
        if(simulationTaskDetails.getTaskType() == "compilation"){
            this.pricePerTarget = origin.getPricePerTargetCompilation();
            this.tasksName = TasksName.COMPILATION;
        }else{
            this.pricePerTarget = origin.getPricePerTargetSimulation();
            this.tasksName = TasksName.SIMULATION;
        }
        this.success = new HashMap<>();
        this.failed = new HashMap<>();
        this.waiting = new HashMap<>();
        this.skipped = new HashMap<>();
        this.frozen = new HashMap<>();
        this.targetStatusMap = new HashMap<>();
        for (Target target : origin.getGraphMap().keySet()){
            targetStatusMap.put(target.getName(), TargetAllStatuses.FROZEN);
        }
        this.chanceWarning = simulationTaskDetails.getChanceWarning();
        this.chanceSuccess = simulationTaskDetails.getChanceSuccess();
        this.isRandom = simulationTaskDetails.isRandom();
        this.processTime = simulationTaskDetails.getProcessTime();
        taskStatus = TaskStatus.FROZEN;
        this.numOfTargetForFinish = workGraph.getGraphMap().size();
        try{
            this.path =  createFolder(tasksName, "c:\\gpup-working-dir");
        }catch (Exception e){
            System.out.println("Error in open folder!!!");
        }
    }

    //copy constructor
    public SimulationTaskOperator(SimulationTaskOperator other, String admin, boolean fromScratch){
        other.upCopy();
        this.name = other.name + String.valueOf(other.getNumOfCopyTasks());
        if(fromScratch){
            this.origin = new Graph(other.origin);
            this.workGraph = new Graph(origin);
        }else{
            this.origin = new Graph(other.getAllSkipAndFail(), other.origin);
            this.workGraph = new Graph(origin);
        }
        this.tasksName = other.tasksName;
        this.admin = admin;
        this.workerMap = new HashMap<>();
        this.pricePerTarget = other.pricePerTarget;
        this.success = new HashMap<>();
        this.failed = new HashMap<>();
        this.waiting = new HashMap<>();
        this.skipped = new HashMap<>();
        this.frozen = new HashMap<>();
        this.targetStatusMap = new HashMap<>();
        for (Target target : origin.getGraphMap().keySet()){
            targetStatusMap.put(target.getName(), TargetAllStatuses.FROZEN);
        }
        this.chanceWarning = other.getChanceWarning();
        this.chanceSuccess = other.getChanceSuccess();
        this.isRandom = other.isRandom();
        this.processTime = other.getProcessTime();
        taskStatus = TaskStatus.FROZEN;
        this.numOfTargetForFinish = workGraph.getGraphMap().size();
        try{
           this.path = createFolder(tasksName, "c:\\gpup-working-dir");
        }catch (Exception e){
            System.out.println("Error in open folder!!!");
        }

    }


}
