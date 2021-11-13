package Enums;

public enum DependencyTypes {
    REQUIRED_FOR ("requiredFor"),
    DEPENDS_ON ("dependsOn");

    private String name;

    /* constructor */
    DependencyTypes(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
