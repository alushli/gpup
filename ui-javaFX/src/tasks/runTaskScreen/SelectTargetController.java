package tasks.runTaskScreen;

import actions.ActionsController;
import appScreen.AppController;
import enums.FxmlPath;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import tasks.TasksController;

import java.net.URL;

public class SelectTargetController extends mainControllers.Controllers {
    private TasksController mainController;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label count_selected_targets;

    @FXML
    private CheckBox what_if_CB;

    @FXML
    private ComboBox<String> direction_CB;

    @FXML
    void clickNext(ActionEvent event) {
        this.mainController.setSelectTaskScreen();
        this.mainController.getTargetsTableController().setSelectDisable();
        this.mainController.setTableButtonDisable();
        this.mainController.setWhatIf(this.what_if_CB.isSelected());
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    public CheckBox getWhat_if_CB() {
        return what_if_CB;
    }

    @FXML
    public void initialize() {
        direction_CB.getItems().removeAll(direction_CB.getItems());
        direction_CB.getItems().addAll("Depends On", "Required For");
        direction_CB.getSelectionModel().select("Depends On");
    }

    public Label getCount_selected_targets() {
        return count_selected_targets;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TasksController mainControllers) {
        this.mainController = mainControllers;
    }


}
