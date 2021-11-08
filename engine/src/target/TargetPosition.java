package target;

public enum TargetPosition {
    ROOT ("root"),
    MIDDLE ("middle"),
    LEAF ("leaf"),
    INDEPENDENT ("independent");

    private String name;

    TargetPosition(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
