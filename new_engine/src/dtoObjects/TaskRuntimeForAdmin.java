package dtoObjects;

import task.TaskOperator;

import java.util.ArrayList;
import java.util.List;

public class TaskRuntimeForAdmin {
   private List<TargetRunFX> targets = new ArrayList<>();
    private int workers;
    private int totalTargets;
    private int doneTargets;
    private String taskStatus;
    private List<String> logs = new ArrayList<>();

    public TaskRuntimeForAdmin(TaskOperator taskOperator){
        this.targets = taskOperator.getAllWithStatus();
        this.workers = taskOperator.getWorkerMap().size();
        this.totalTargets = targets.size();
        this.doneTargets = totalTargets - taskOperator.getNumOfTargetForFinish();
        this.taskStatus = taskOperator.getTaskStatus().toString();
        this.logs = taskOperator.getLogs();
    }

    public List<TargetRunFX> getTargets() {
        return targets;
    }

    public int getWorkers() {
        return workers;
    }

    public int getTotalTargets() {
        return totalTargets;
    }

    public int getDoneTargets() {
        return doneTargets;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public List<String> getLogs() {
        return logs;
    }
}
