package dtoObjects;

import java.util.List;

public class NewCompilationTaskDetails {
    private String taskName;
    private String taskType;
    private String graphName;
    private String[] targets;
    private String sourceFolder;
    private String productFolder;

    public NewCompilationTaskDetails(String taskName, String taskType, String graphName, String[] targets, String sourceFolder, String productFolder) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.graphName = graphName;
        this.targets = targets;
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getProductFolder() {
        return productFolder;
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
}
