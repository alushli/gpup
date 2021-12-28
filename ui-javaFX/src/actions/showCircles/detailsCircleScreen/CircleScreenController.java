package actions.showCircles.detailsCircleScreen;

import actions.showCircles.ShowCirclesController;
import actions.showPaths.ShowPathsController;
import appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class CircleScreenController extends mainControllers.Controllers {
    private ShowCirclesController mainController;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label target_label;

    @FXML
    private Label paths_message_label;

    @FXML
    private TextArea paths_TA;

    @FXML
    void clickFind(ActionEvent event) {

    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowCirclesController mainControllers) {
        this.mainController = mainControllers;
    }

}
