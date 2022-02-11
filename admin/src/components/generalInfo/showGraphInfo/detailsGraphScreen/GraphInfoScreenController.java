package components.generalInfo.showGraphInfo.detailsGraphScreen;

import components.appScreen.AppController;
import components.adminEnums.AppFxmlPath;
import components.generalComponents.serialSetTable.SerialSetTableController;
import components.generalInfo.GeneralInfoController;
import components.generalInfo.showGraphInfo.ShowGraphInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;

public class GraphInfoScreenController extends components.mainControllers.Controllers{
    private ShowGraphInfoController mainController;
    private String fullPathExport;
    private Stage popupWindow;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label graph_name_label;


    @FXML
    private Label target_num_label;

    @FXML
    private Label root_num_label;

    @FXML
    private Label mid_num_label;

    @FXML
    private Label leaf_num_label;

    @FXML
    private Label ind_num_label;


    public void setFullPathExport(String fullPathExport) {
        this.fullPathExport = fullPathExport;
    }

    public ShowGraphInfoController getMainController() {
        return mainController;
    }


    public Stage getPopupWindow() {
        return popupWindow;
    }

    public void exitPopup(){
        this.popupWindow.close();
    }

    public Label getGraph_name_label() {
        return graph_name_label;
    }

    public Label getTarget_num_label() {
        return target_num_label;
    }

    public Label getRoot_num_label() {
        return root_num_label;
    }

    public Label getMid_num_label() {
        return mid_num_label;
    }

    public Label getLeaf_num_label() {
        return leaf_num_label;
    }

    public Label getInd_num_label() {
        return ind_num_label;
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowGraphInfoController mainControllers) {
        this.mainController = mainControllers;
    }

    public AppController getAppController(){ return this.appController; }
}
