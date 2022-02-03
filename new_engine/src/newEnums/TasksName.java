package newEnums;

public enum TasksName {
    SIMULATION ("Simulation"),
    COMPILATION("Compilation");

    private String name;

    /* constructor */
    TasksName(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
