package tasks.runTaskScreen;

import appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class TargetController extends mainControllers.Controllers{
    @FXML
    private StackPane target_SP;

    @FXML
    void clickTarget(ActionEvent event) {

    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
