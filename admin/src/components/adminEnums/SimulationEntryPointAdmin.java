package components.adminEnums;

public enum SimulationEntryPointAdmin {
    FROM_SCRATCH ("From Scratch"),
    INCREMENTAL ("Incremental");

    private String name;

    /* constructor */
    SimulationEntryPointAdmin(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
