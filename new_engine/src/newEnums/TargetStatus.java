package newEnums;

public enum TargetStatus {
    FROZEN ("frozen"),
    SKIPPED ("skipped"),
    WAITING ("waiting"),
    IN_PROCESS("in-process"),
    FINISHED ("finished"),
    NONE ("none");

    private String name;

    /* constructor */
    TargetStatus(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
