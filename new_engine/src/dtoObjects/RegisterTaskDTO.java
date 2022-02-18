package dtoObjects;

import task.CompilationTaskOperator;
import task.SimulationTaskOperator;
import task.TaskOperator;

public class RegisterTaskDTO {
    private String taskName;
    private String taskType;
    private int pricePerTarget;
    private String taskStatus;
    private int totalTargets;

    // for simulation:
    private int targetProcessingTime;
    private boolean isRandomTime;
    private double successRate;
    private double warningRate;

    // for compilation:
    private String sourceFolder;
    private String productFolder;

    private int numOfWorkers;

    public RegisterTaskDTO(TaskOperator taskOperator){
        this.numOfWorkers = taskOperator.getWorkerMap().size();
        this.taskName = taskOperator.getName();
        if(taskOperator instanceof SimulationTaskOperator){
            SimulationTaskOperator simulationTaskOperator = (SimulationTaskOperator)taskOperator;
            this.targetProcessingTime = simulationTaskOperator.getProcessTime();
            this.isRandomTime = simulationTaskOperator.isRandom();
            this.successRate = simulationTaskOperator.getChanceSuccess();
            this.warningRate = simulationTaskOperator.getChanceWarning();
            taskType = "Simulation";
        }else{
            CompilationTaskOperator compilationTaskOperator = (CompilationTaskOperator)taskOperator;
            this.sourceFolder = compilationTaskOperator.getSourceFolder();
            this.productFolder = compilationTaskOperator.getProductFolder();
            taskType = "Compilation";
        }
        this.pricePerTarget = taskOperator.getPricePerTarget();
        this.taskStatus = taskOperator.getTaskStatus().toString();
        this.totalTargets = taskOperator.getAllWithStatus().size();
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public int getPricePerTarget() {
        return pricePerTarget;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public int getTargetProcessingTime() {
        return targetProcessingTime;
    }

    public boolean isRandomTime() {
        return isRandomTime;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public double getWarningRate() {
        return warningRate;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getProductFolder() {
        return productFolder;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getTotalTargets() {
        return totalTargets;
    }

    public void setPricePerTarget(int pricePerTarget) {
        this.pricePerTarget = pricePerTarget;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTargetProcessingTime(int targetProcessingTime) {
        this.targetProcessingTime = targetProcessingTime;
    }

    public void setRandomTime(boolean randomTime) {
        isRandomTime = randomTime;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public void setWarningRate(double warningRate) {
        this.warningRate = warningRate;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public void setProductFolder(String productFolder) {
        this.productFolder = productFolder;
    }


}
