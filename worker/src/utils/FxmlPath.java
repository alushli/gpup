package utils;

public enum FxmlPath {
    LOGIN("/components/login/workerLogin.fxml");

    private String name;

    /* constructor */
    FxmlPath(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
