package components.workerEnums;

public enum AppFxmlPath {
    MENU("/components/menu/menu.fxml"),
    DASHBOARD_USERS_TABLE("/components/generalComponents/usersTable/usersTable.fxml"),
    DASHBOARD_SCREEN("/components/dashboard/dashboard.fxml"),
    APP_SCREEN("/components/appScreen/app.fxml"),
    TARGETS_TABLE("/components/generalComponents/targetsTask/targetsTask.fxml"),
    WORK_TASK_TABLE("/components/generalComponents/workerTasks/workerTasks.fxml"),
    TASK_MANAGEMENT("/components/task/taskManagment/workerTaskMenegement.fxml"),
    DASHBOARD_TASK_TABLE("/components/generalComponents/taskTable/workerTasksTable.fxml");

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
