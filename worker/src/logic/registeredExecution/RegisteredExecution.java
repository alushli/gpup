package logic.registeredExecution;

import dtoObjects.RegisterTaskDTO;
import newEnums.TaskStatus;
import newEnums.TasksName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredExecution {
    private String name;
    private TasksName taskName;
    private int pricePerTarget;
    private int targetIPerformed;
    private TaskStatus taskStatus;
    private int workers;
    private int doneTargets;
    private int totalTargets;

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
        this.pricePerTarget = registerTaskDTO.getPricePerTarget();
        this.totalTargets = registerTaskDTO.getTotalTargets();
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

    public void setTaskStatus(String status){
        if(status.equals("Done")){
            this.taskStatus = TaskStatus.DONE;
        }else if(status.equals("In process")){
            this.taskStatus = TaskStatus.IN_PROCESS;
        }else if(status.equals("Paused")) {
            this.taskStatus = TaskStatus.PAUSED;
        }else if(status.equals("Cancle")){
            this.taskStatus = TaskStatus.CANCLE;
        }else if(status.equals("Frozen")){
            this.taskStatus = TaskStatus.FROZEN;
        }
    }

    public int getWorkers() {
        return workers;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public int getDoneTargets() {
        return doneTargets;
    }

    public void setDoneTargets(int doneTargets) {
        this.doneTargets = doneTargets;
    }

    public int getTotalTargets() {
        return totalTargets;
    }

    public void setTotalTargets(int totalTargets) {
        this.totalTargets = totalTargets;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void addToDoneTargetsByMe(){
        this.targetIPerformed++;
    }


}
