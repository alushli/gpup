package components.generalComponents.taskTargetTable;

public class TargetRunFX {
    private String name;
    private String position;
    private String worker;
    private String runStatus;
    private String finishStatus;

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getWorker() {
        return worker;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public TargetRunFX(String name, String position, String worker,
                    String runStatus, String finishStatus)  {
        this.name = name;
        this.position = position;
        this.worker = worker;
        this.runStatus = runStatus;
        this.finishStatus = finishStatus;
    }

    public TargetRunFX(TargetRunFX other){
        this.name = other.name;
        this.position = other.position;
        this.worker = other.worker;
        this.runStatus = other.runStatus;
        this.finishStatus = other.finishStatus;
    }
}
