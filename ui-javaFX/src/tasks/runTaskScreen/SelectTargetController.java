package tasks.runTaskScreen;

import appScreen.AppController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import tasks.TasksController;

public class SelectTargetController extends mainControllers.Controllers {
    private TasksController mainController;
    private StringProperty skin;
    private BooleanProperty oneTargetSelect;

    @FXML
    private Button next_btn;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private CheckBox what_if_CB;

    @FXML
    private ComboBox<String> direction_CB;

    public void skinListener(){
        this.mainController.setLightListener(this.skin, this.fall_screen_SP);
    }

    @FXML
    void clickDirection(ActionEvent event) {
        if(this.direction_CB.getValue().equals("Depends On"))
            this.mainController.setWhatIfTableDirection("dependsOn");
        else
            this.mainController.setWhatIfTableDirection("requiredFor");
    }

    @FXML
    void clickWhatIf(ActionEvent event) {
        this.mainController.setWhatIf(this.what_if_CB.isSelected());
    }

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

    public StringProperty skinProperty() {
        return skin;
    }

    public BooleanProperty oneTargetSelectProperty() {
        return oneTargetSelect;
    }

    @FXML
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
        this.oneTargetSelect = new SimpleBooleanProperty(false);
        direction_CB.getItems().removeAll(direction_CB.getItems());
        direction_CB.getItems().addAll("Depends On", "Required For");
        direction_CB.getSelectionModel().select(0);
        this.next_btn.disableProperty().bind(this.oneTargetSelect.not());
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TasksController mainControllers) {
        this.mainController = mainControllers;
    }
}
