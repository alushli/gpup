package newEnums;

public enum TargetRuntimeStatus {

    FROZEN ("frozen"),
    WAITING ("waiting"),
    SKIPPED ("skipped"),
    IN_PROCESS("in process"),
    FINISHED("finished");

    private String name;

    /* constructor */
    TargetRuntimeStatus(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
