package actions.showPaths.detailsPathsScreen;

import actions.showPaths.ShowPathsController;
import appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class PathsScreenController extends mainControllers.Controllers{
    private ShowPathsController mainController;

    @FXML
    private Label target1_label;

    @FXML
    private Label target2_label;

    @FXML
    private Label paths_message_label;

    @FXML
    private ComboBox<String> direction_CB;

    @FXML
    private TextArea paths_TA;

    @FXML
    private StackPane fall_screen_SP;

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @FXML
    void clickFind(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        setPathsTable(5);
        direction_CB.getItems().removeAll(direction_CB.getItems());
        direction_CB.getItems().addAll("Depends On", "Required For");
        direction_CB.getSelectionModel().select("Depends On");
    }

    @FXML
    void clickSwitch(ActionEvent event) {
        String temp = target2_label.getText();
        target2_label.setText(target1_label.getText());
        target1_label.setText(temp);
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowPathsController mainControllers) {
        this.mainController = mainControllers;
    }

    void setPathsTable(int size){
        for(int i=0 ; i<size ; i++){
            paths_TA.appendText(i+1 + ": Please upload valid xml file first \n");
        }
    }
}
