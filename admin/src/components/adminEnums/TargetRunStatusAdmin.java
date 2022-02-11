package components.adminEnums;

public enum TargetRunStatusAdmin {
    SUCCESS ("success"),
    WARNING ("warning"),
    FAILURE ("failure"),
    SKIPPED ("skipped"),
    NONE ("none");

    private String name;

    /* constructor */
    TargetRunStatusAdmin(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
