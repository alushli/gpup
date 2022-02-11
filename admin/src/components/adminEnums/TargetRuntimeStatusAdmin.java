package components.adminEnums;

public enum TargetRuntimeStatusAdmin {

    FROZEN ("frozen"),
    WAITING ("waiting"),
    SKIPPED ("skipped"),
    IN_PROCESS("in process"),
    FINISHED("finished");

    private String name;

    /* constructor */
    TargetRuntimeStatusAdmin(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
