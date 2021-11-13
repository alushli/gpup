package Enums;

public enum TargetStatus {
    FROZEN ("frozen"),
    SKIPPED ("skipped"),
    WAITING ("waiting"),
    IN_PROCESS("in-process"),
    FINISHED ("finished");

    private String name;

    TargetStatus(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
