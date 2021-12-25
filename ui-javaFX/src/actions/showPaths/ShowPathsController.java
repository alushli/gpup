package actions.showPaths;

import actions.ActionsController;
import appScreen.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowPathsController extends mainControllers.Controllers{
    private ActionsController mainController;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(ActionsController mainControllers) {
        this.mainController = mainControllers;
    }
}
