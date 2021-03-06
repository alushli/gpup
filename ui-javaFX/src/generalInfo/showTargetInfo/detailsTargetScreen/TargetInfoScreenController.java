package generalInfo.showTargetInfo.detailsTargetScreen;

import appScreen.AppController;
import enums.StyleSheetsPath;
import generalInfo.showTargetInfo.ShowTargetInfoController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Set;

public class TargetInfoScreenController extends mainControllers.Controllers{
    private ShowTargetInfoController mainController;
    private StringProperty skin;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label target_name_label;

    @FXML
    private Label position_label;

    @FXML
    private ListView<String> direct_DO_list;

    @FXML
    private ListView<String> total_DO_list;

    @FXML
    private ListView<String> direct_RF_list;

    @FXML
    private ListView<String> total_RF_list;

    @FXML
    private TextArea free_text_TA;

    @FXML
    private GridPane target_info_grid;

    @FXML
    private ListView<String> serial_list;

    @FXML
    private Label error_message;

    public ListView<String> getSerial_list() {
        return serial_list;
    }

    public ListView<String> getTotal_DO_list() {
        return total_DO_list;
    }

    public ListView<String> getTotal_RF_list() {
        return total_RF_list;
    }

    @FXML
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
    }

    public void setSerialSet(Set<String> set){
        if(!set.isEmpty()) {
            for (String str : set) {
                this.serial_list.getItems().add(str);
            }
        }
    }

    public void skinListener(){
        this.mainController.skinListener(this.skin, this.fall_screen_SP);
    }

    public StringProperty skinProperty() {
        return skin;
    }

    public Label getError_message() {
        return error_message;
    }

    public Label getTarget_name_label() {
        return target_name_label;
    }

    public ListView<String> getDirect_DO_list() {
        return direct_DO_list;
    }

    public GridPane getTarget_info_grid() {
        return target_info_grid;
    }

    public ListView<String> getDirect_RF_list() {
        return direct_RF_list;
    }

    public Label getPosition_label() {
        return position_label;
    }

    public TextArea getFree_text_TA() {
        return free_text_TA;
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowTargetInfoController mainControllers) {
        this.mainController = mainControllers;
    }
}
