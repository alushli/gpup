package dtoObjects;

import task.TaskOperator;

public class WorkerTaskDTO {
    private String taskName;
    private int workers;
    private int totalTargets;
    private int doneTargets;
    private String status;


    public WorkerTaskDTO(TaskOperator taskOperator){
        this.taskName = taskOperator.getName();
        this.workers = taskOperator.getWorkerMap().size();
        this.totalTargets = taskOperator.getAllWithStatus().size();
        this.doneTargets = totalTargets - taskOperator.getNumOfTargetForFinish();
        this.status = taskOperator.getTaskStatus().toString();
    }

    public String getTaskName() {
        return taskName;
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

    public String getStatus() {
        return status;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public void setTotalTargets(int totalTargets) {
        this.totalTargets = totalTargets;
    }

    public void setDoneTargets(int doneTargets) {
        this.doneTargets = doneTargets;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
