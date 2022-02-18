package dtoObjects;

import newEnums.TasksName;
import target.Target;
import task.SimulationTaskOperator;
import task.TaskOperator;

public class TargetToRunWorker {
    private String targetName;
    private String taskName;
    private String taskType;
    private int price;
    private String generalInfo;


    public TargetToRunWorker(Target target, TaskOperator taskOperator){
        this.targetName = target.getName();
        if(taskOperator instanceof SimulationTaskOperator){
            this.taskType = TasksName.SIMULATION.toString();
        }else{
            this.taskType = TasksName.COMPILATION.toString();
        }
        this.price = taskOperator.getPricePerTarget();
        this.generalInfo = target.getGeneralInfo();
        this.taskName = taskOperator.getName();
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public int getPrice() {
        return price;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }
}
