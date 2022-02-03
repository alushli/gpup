package newEnums;

public enum SimulationEntryPoint {
    FROM_SCRATCH ("From Scratch"),
    INCREMENTAL ("Incremental");

    private String name;

    /* constructor */
    SimulationEntryPoint(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
