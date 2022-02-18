package logic.utils;

import java.util.ArrayList;
import java.util.List;

public class LogsManager {
    private List<String> logs = new ArrayList<>();
    private String targetName;

    public LogsManager(String targetName) {
        this.targetName = targetName;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void addToLogs(String log){
        logs.add(log);
    }
}
