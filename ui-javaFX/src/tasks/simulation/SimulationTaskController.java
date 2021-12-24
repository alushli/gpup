package tasks.simulation;

import appScreen.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tasks.TasksController;

public class SimulationTaskController extends mainControllers.Controllers{
    private TasksController mainController;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TasksController mainControllers) {
        this.mainController = mainControllers;
    }
}
