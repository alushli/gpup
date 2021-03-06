package components.adminEnums;

public enum AppFxmlPath {
    LOAD_FILE("/components/loadFile/loadFile.fxml"),
    ACTIONS("/components/actions/actions.fxml"),
    MENU("/components/menu/menu.fxml"),
    SUB_MENU("/components/menu/subMenu/subMenu.fxml"),
    TASKS("/components/tasks/createNewTask.fxml"),
    TASKS_MAIN_SCREEN("/components/tasks/tasksAdmin.fxml"),
    TASKS_MANAGEMENT("/components/tasks/taskManagement/tasksManagment.fxml"),
    TASKS_MANAGEMENT_DETAILS("/components/tasks/taskManagement/tasksManagmentsDetails.fxml"),
    PRE_TASKS("/components/tasks/preTaskAdmin.fxml"),
    GENERAL_INFO("/components/generalInfo/generalInfo.fxml"),
    APP_SCREEN("/components/appScreen/app.fxml"),
    TARGET_PATHS("/components/actions/showPaths/showPaths.fxml"),
    TARGET_CIRCLES("/components/actions/showCircles/showCircles.fxml"),
    TARGET_INFO("/components/generalInfo/showTargetInfo/showTargetInfo.fxml"),
    GRAPH_INFO("/components/generalInfo/showGraphInfo/showGraphInfo.fxml"),
    DETAILS_PATH_SCREEN("/components/actions/showPaths/detailsPathsScreen/pathsScreen.fxml"),
    DETAILS_CIRCLE_SCREEN("/components/actions/showCircles/detailsCircleScreen/circleScreen.fxml"),
    DETAILS_GRAPH_INFO_SCREEN("/components/generalInfo/showGraphInfo/detailsGraphScreen/graphInfoScreen.fxml"),
    DETAILS_TARGET_INFO_SCREEN("/components/generalInfo/showTargetInfo/detailsTargetScreen/targetInfoScreen.fxml"),
    TARGET_TABLE("/components/generalComponents/targetsTable/TargetsTable.fxml"),
    SERIAL_SET_TABLE("/components/generalComponents/serialSetTable/serialSetTable.fxml"),
    TASK_SELECT_TARGET("/components/tasks/runTaskScreen/selectTargets.fxml"),
    TASK_SELECT_TASK("/components/tasks/runTaskScreen/selectTaskScreen.fxml"),
    TASK_RUN_TASK("/components/tasks/runTaskScreen/runTask.fxml"),
    TARGET_BOX_TASK("/components/tasks/runTaskScreen/target.fxml"),
    TARGET_INFO_POPUP_TASK("/components/tasks/runTaskScreen/targetInfo.fxml"),
    EXPORT_GRAPH_POPUP("/components/generalInfo/showGraphInfo/detailsGraphScreen/exportGraphScreen/exportGraphPopup.fxml"),
    DASHBOARD_SCREEN("/components/dashboard/dashboard.fxml"),
    DASHBOARD_USERS_TABLE("/components/generalComponents/usersTable/usersTable.fxml"),
    DASHBOARD_GRAPH_TABLE("/components/generalComponents/graphsTable/graphTable.fxml"),
    TASKS_TABLE("/components/generalComponents/tasksTable/tasksTable.fxml"),
    TASKS_TARGET_WAITING_TABLE("/components/generalComponents/taskTargetTable/waitingTargetsTable.fxml"),
    TASKS_TARGET_PROCESS_TABLE("/components/generalComponents/taskTargetTable/processTargetsTable.fxml"),
    TASKS_TARGET_DONE_TABLE("/components/generalComponents/taskTargetTable/doneTargetsTable.fxml");

    private String name;

    /* constructor */
    AppFxmlPath(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
