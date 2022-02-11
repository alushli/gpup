package logic.registeredExecution;

import newEnums.TaskStatus;
import newEnums.TasksName;

public class RegisteredExecution {
    private String name;
    private TasksName tasksName;
    private int pricePerTarget;
    private int targetIPerformed;
    private TaskStatus taskStatus;

    // for simulation:
    private int targetProcessingTime;
    private boolean isRandomTime;
    private float successRate;
    private float warningRate;

    // for compilation:
    private String sourceFolder;
    private String productFolder;

    public RegisteredExecution(String name, TasksName tasksName, int pricePerTarget, int targetIPerformed, int targetProcessingTime,
                               boolean isRandomTime, float successRate, float warningRate, String sourceFolder, String productFolder, TaskStatus taskStatus) {
        this.name = name;
        this.tasksName = tasksName;
        this.pricePerTarget = pricePerTarget;
        this.targetIPerformed = targetIPerformed;
        this.targetProcessingTime = targetProcessingTime;
        this.isRandomTime = isRandomTime;
        this.successRate = successRate;
        this.warningRate = warningRate;
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
        this.taskStatus = taskStatus;
    }

    public int getTotalPriceFromThisExecution() {
        return this.targetIPerformed * this.pricePerTarget;
    }

    public String getName() {
        return name;
    }

    public TasksName getTasksName() {
        return tasksName;
    }

    public int getPricePerTarget() {
        return pricePerTarget;
    }

    public int getTargetIPerformed() {
        return targetIPerformed;
    }

    public int getTargetProcessingTime() {
        return targetProcessingTime;
    }

    public boolean isRandomTime() {
        return isRandomTime;
    }

    public float getSuccessRate() {
        return successRate;
    }

    public float getWarningRate() {
        return warningRate;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getProductFolder() {
        return productFolder;
    }
}
