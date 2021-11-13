package Enums;

public enum SimulationEntryPoint {
    FROM_SCRATCH ("From Scratch"),
    INCREMENTAL ("Incremental");

    private String name;

    SimulationEntryPoint(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
