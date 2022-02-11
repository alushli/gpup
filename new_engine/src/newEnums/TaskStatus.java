package newEnums;

public enum TaskStatus {
    DONE ("Done"),
    IN_PROCESS("In process"),
    PAUSED("Paused"),
    FROZEN("Frozen");

    private String name;

    /* constructor */
    TaskStatus(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
