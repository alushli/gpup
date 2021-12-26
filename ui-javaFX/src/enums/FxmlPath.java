package enums;

public enum FxmlPath {
    LOAD_FILE("loadFile/loadFile.fxml"),
    ACTIONS("actions/actions.fxml"),
    MENU("menu/menu.fxml"),
    SUB_MENU("subMenu/subMenu.fxml"),
    TASKS("tasks/tasks.fxml"),
    GENERAL_INFO("generalInfo/generalInfo.fxml"),
    APP_SCREEN("appScreen/app.fxml"),
    TARGET_PATHS("actions/showPaths/showPaths.fxml"),
    TARGET_CIRCLES("actions/showCircles/showCircles.fxml"),
    TARGET_INFO("generalInfo/showTargetInfo/showTargetInfo.fxml"),
    GRAPH_INFO("generalInfo/showGraphInfo/showGraphInfo.fxml"),
    SIMULATION_TASK("tasks/simulation/simulationTask.fxml"),
    COMPILER_TASK("tasks/compiler/compilerTask.fxml"),
    DETAILS_PATH_SCREEN("detailsScreen/pathsScreen.fxml"),
    TARGET_TABEL("generalComponents/targetsTable/TargetsTable.fxml");

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
