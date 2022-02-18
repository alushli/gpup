package logic.taskManager;

import dtoObjects.TargetToRunWorker;
import logic.utils.LogsManager;
import newEnums.TargetAllStatuses;
import newEnums.TargetRunStatus;

public class RunTimeTargetDetails {
    private String taskName;
    private String taskType;
    private String targetName;
    private String runStatus;
    private LogsManager logsManager;
    private int credit;

    public RunTimeTargetDetails(TargetToRunWorker target){
        this.targetName = target.getTargetName();
        this.taskName = target.getTaskName();
        this.taskType = target.getTaskType();
        this.runStatus = TargetAllStatuses.IN_PROCESS.toString();
        this.logsManager = new LogsManager(targetName);
        this.credit = target.getPrice();
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public LogsManager getLogsManager() {
        return logsManager;
    }

    public int getCredit() {
        return credit;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }
}
