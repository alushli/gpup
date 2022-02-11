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
        }
        this.processTime = processTime;
        this.isRandom = isRandom;
        this.chanceSuccess = chanceSuccess;
        this.chanceWarning = chanceWarning;
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
            this.pricePerTarget = origin.getPricePerTargetCompilation() * origin.getGraphMap().size();
            this.tasksName = TasksName.COMPILATION;
        }else{
            this.pricePerTarget = origin.getPricePerTargetSimulation() * origin.getGraphMap().size();
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
    }

}
