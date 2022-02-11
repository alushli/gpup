package dtoObjects;

public class NewSimulationTaskDetails {
    private String taskName;
    private String taskType;
    private String graphName;
    private String[] targets;

    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;

    public NewSimulationTaskDetails(String taskName, String taskType, String graphName, String[] targets, int processTime, boolean isRandom, double chanceSuccess, double chanceWarning) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.graphName = graphName;
        this.targets = targets;
        this.processTime = processTime;
        this.isRandom = isRandom;
        this.chanceSuccess = chanceSuccess;
        this.chanceWarning = chanceWarning;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getGraphName() {
        return graphName;
    }

    public String[] getTargets() {
        return targets;
    }

    public int getProcessTime() {
        return processTime;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public double getChanceSuccess() {
        return chanceSuccess;
    }

    public double getChanceWarning() {
        return chanceWarning;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public void setTargets(String[] targets) {
        this.targets = targets;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public void setRandom(boolean random) {
        isRandom = random;
    }

    public void setChanceSuccess(double chanceSuccess) {
        this.chanceSuccess = chanceSuccess;
    }

    public void setChanceWarning(double chanceWarning) {
        this.chanceWarning = chanceWarning;
    }
}
