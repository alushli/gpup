package generalInfo.showTargetInfo.detailsTargetScreen;

import appScreen.AppController;
import generalInfo.showGraphInfo.ShowGraphInfoController;
import generalInfo.showTargetInfo.ShowTargetInfoController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class TargetInfoScreenController extends mainControllers.Controllers{
    private ShowTargetInfoController mainController;

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
    private ListView<String> serial_list;


    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowTargetInfoController mainControllers) {
        this.mainController = mainControllers;
    }
}
