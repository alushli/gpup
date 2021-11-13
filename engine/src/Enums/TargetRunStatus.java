package Enums;

public enum TargetRunStatus {
    SUCCESS ("success"),
    WARNING ("warning"),
    FAILURE ("failure"),
    NONE ("none");

    private String name;

    TargetRunStatus(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
