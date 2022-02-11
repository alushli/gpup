package components.tasks;

import components.appScreen.AppController;

public class TaskManagmentController extends components.mainControllers.Controllers{
    private TasksAdminController mainController;
    public void setMainController(TasksAdminController mainControllers) {
        this.mainController = mainControllers;
    }

    public TasksAdminController getMainController() {
        return mainController;
    }


    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
