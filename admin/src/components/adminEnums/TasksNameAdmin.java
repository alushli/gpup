package components.adminEnums;

public enum TasksNameAdmin {
    SIMULATION ("Simulation"),
    COMPILATION("Compilation");

    private String name;

    /* constructor */
    TasksNameAdmin(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
