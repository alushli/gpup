package newEnums;

public enum TargetAllStatuses {
    FROZEN ("frozen"),
    WAITING ("waiting"),
    SKIPPED ("skipped"),
    IN_PROCESS("in process"),
    SUCCESS("success"),
    WARNING("warning"),
    FAILED("failed");

    private String name;

    /* constructor */
    TargetAllStatuses(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }

}
