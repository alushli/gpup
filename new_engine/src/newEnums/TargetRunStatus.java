package newEnums;

public enum TargetRunStatus {
    SUCCESS ("success"),
    WARNING ("warning"),
    FAILURE ("failure"),
    SKIPPED ("skipped"),
    IN_PROCESS("in-process"),
    NONE ("none");

    private String name;

    /* constructor */
    TargetRunStatus(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
