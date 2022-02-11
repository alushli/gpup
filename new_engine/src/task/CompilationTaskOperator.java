package task;

import User.Admin;
import dtoObjects.NewCompilationTaskDetails;
import graph.Graph;
import newEnums.TargetAllStatuses;
import newEnums.TaskStatus;
import newEnums.TasksName;
import target.Target;

import java.util.HashMap;

public class CompilationTaskOperator extends TaskOperator{
    private String sourceFolder;
    private String productFolder;

    public CompilationTaskOperator(Graph graph, String name, String admin, int pricePerTarget, String sourceFolder, String productFolder){
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
        this.productFolder = productFolder;
        this.sourceFolder = sourceFolder;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getProductFolder() {
        return productFolder;
    }

    public CompilationTaskOperator(NewCompilationTaskDetails compilationTaskDetails, String admin, Graph originGraph){
        this.name = compilationTaskDetails.getTaskName();
        this.origin = new Graph(compilationTaskDetails.getTargets(),originGraph );
        this.workGraph = new Graph(origin);
        this.admin = admin;
        this.workerMap = new HashMap<>();
        if(compilationTaskDetails.getTaskType() == "compilation"){
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
        this.productFolder = compilationTaskDetails.getProductFolder();
        this.sourceFolder = compilationTaskDetails.getSourceFolder();
        taskStatus = TaskStatus.FROZEN;
        this.numOfTargetForFinish = workGraph.getGraphMap().size();
    }


}
