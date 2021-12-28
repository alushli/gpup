package enums;

public enum FxmlPath {
    LOAD_FILE("/loadFile/loadFile.fxml"),
    ACTIONS("/actions/actions.fxml"),
    MENU("/menu/menu.fxml"),
    SUB_MENU("/menu/subMenu/subMenu.fxml"),
    TASKS("/tasks/tasks.fxml"),
    GENERAL_INFO("/generalInfo/generalInfo.fxml"),
    APP_SCREEN("/appScreen/app.fxml"),
    TARGET_PATHS("/actions/showPaths/showPaths.fxml"),
    TARGET_CIRCLES("/actions/showCircles/showCircles.fxml"),
    TARGET_INFO("/generalInfo/showTargetInfo/showTargetInfo.fxml"),
    GRAPH_INFO("/generalInfo/showGraphInfo/showGraphInfo.fxml"),
    SIMULATION_TASK("/tasks/simulation/simulationTask.fxml"),
    COMPILER_TASK("/tasks/compiler/compilerTask.fxml"),
    DETAILS_PATH_SCREEN("/actions/showPaths/detailsPathsScreen/pathsScreen.fxml"),
    DETAILS_CIRCLE_SCREEN("/actions/showCircles/detailsCircleScreen/circleScreen.fxml"),
    DETAILS_GRAPH_INFO_SCREEN("/generalInfo/showGraphInfo/detailsGraphScreen/graphInfoScreen.fxml"),
    DETAILS_TARGET_INFO_SCREEN("/generalInfo/showTargetInfo/detailsTargetScreen/targetInfoScreen.fxml"),
    TARGET_TABLE("/generalComponents/targetsTable/TargetsTable.fxml"),
    SERIAL_SET_TABLE("/generalComponents/serialSetTable/serialSetTable.fxml");

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
