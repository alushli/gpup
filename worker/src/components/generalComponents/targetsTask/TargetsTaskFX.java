package components.generalComponents.targetsTask;

public class TargetsTaskFX {
    private String name;
    private String taskName;
    private String taskType;
    private String taskStatus;
    private Integer price;

    public String getName() {
        return name;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public Integer getPrice() {
        return price;
    }

    public TargetsTaskFX(String name, String taskName, String taskType,
                       String taskStatus, Integer price)  {
        this.name = name;
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.price = price;
    }

    public TargetsTaskFX(TargetsTaskFX other){
        this.name = other.name;
        this.taskName = other.taskName;
        this.taskType = other.taskType;
        this.taskStatus = other.taskStatus;
        this.price = other.price;
    }
}
