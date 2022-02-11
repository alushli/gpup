package logic.registeredExecution;

import dtoObjects.RegisterTaskDTO;
import newEnums.TaskStatus;
import newEnums.TasksName;

public class RegisteredExecution {
    private String name;
    private TasksName taskName;
    private int pricePerTarget;
    private int targetIPerformed;
    private TaskStatus taskStatus;

    // for simulation:
    private int targetProcessingTime;
    private boolean isRandomTime;
    private double successRate;
    private double warningRate;

    // for compilation:
    private String sourceFolder;
    private String productFolder;

    public RegisteredExecution(String name, TasksName tasksName, int pricePerTarget, int targetProcessingTime,
                               boolean isRandomTime, double successRate, double warningRate, String sourceFolder, String productFolder, TaskStatus taskStatus) {
        this.name = name;
        this.taskName = tasksName;
        this.pricePerTarget = pricePerTarget;
        this.targetIPerformed = 0;
        this.targetProcessingTime = targetProcessingTime;
        this.isRandomTime = isRandomTime;
        this.successRate = successRate;
        this.warningRate = warningRate;
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
        this.taskStatus = taskStatus;
    }

    public RegisteredExecution(RegisterTaskDTO registerTaskDTO){
        this.name = registerTaskDTO.getTaskName();
        if(registerTaskDTO.getTaskType().equalsIgnoreCase("Simulation")){
           this.taskName = TasksName.SIMULATION;
           this.targetProcessingTime = registerTaskDTO.getTargetProcessingTime();
           this.isRandomTime = registerTaskDTO.isRandomTime();
           this.successRate = registerTaskDTO.getSuccessRate();
           this.warningRate = registerTaskDTO.getWarningRate();
        }else{
            this.taskName = TasksName.COMPILATION;
            this.productFolder = registerTaskDTO.getProductFolder();
            this.sourceFolder = registerTaskDTO.getSourceFolder();
        }
    }

    public int getTotalPriceFromThisExecution() {
        return this.targetIPerformed * this.pricePerTarget;
    }

    public String getName() {
        return name;
    }

    public TasksName getTasksName() {
        return taskName;
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


}
