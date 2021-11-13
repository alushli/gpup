package Enums;

public enum TargetPosition {
    ROOT ("root"),
    MIDDLE ("middle"),
    LEAF ("leaf"),
    INDEPENDENT ("independent");

    private String name;

    /* constructor */
    TargetPosition(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
