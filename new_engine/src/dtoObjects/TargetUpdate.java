package dtoObjects;

import java.util.List;

public class TargetUpdate {
    private String targetName;
    private String taskName;
    private String status;
    private List<String> logs;

    public TargetUpdate(String targetName, String taskName, String status, List<String> logs) {
        this.targetName = targetName;
        this.taskName = taskName;
        this.status = status;
        this.logs = logs;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
