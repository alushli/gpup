package Enums;

public enum DependencyTypes {
    REQUIRED_FOR ("requiredFor"),
    DEPENDS_ON ("dependsOn");

    private String name;

    DependencyTypes(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
