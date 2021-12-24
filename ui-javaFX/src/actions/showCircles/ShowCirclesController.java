package actions.showCircles;

import actions.ActionsController;
import appScreen.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowCirclesController extends mainControllers.Controllers{
    private ActionsController mainController;//means - actionController

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ActionsController mainControllers) {
        this.mainController = mainControllers;
    }
}
