package generalInfo.showGraphInfo.detailsGraphScreen;

import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.serialSetTable.SerialSetTableController;
import generalComponents.targetsTable.TargetsTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showGraphInfo.ShowGraphInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.net.URL;

public class GraphInfoScreenController extends mainControllers.Controllers{
    private ShowGraphInfoController mainController;

    @FXML
    private StackPane fall_screen_SP;

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

    @FXML
    private Label serial_label;

    @FXML
    private StackPane table_SP;

    @FXML
    void clickExport(ActionEvent event) {

    }

    public void setSerialSetTable(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.SERIAL_SET_TABLE.toString());
            fxmlLoader.setLocation(url);
            GeneralInfoController generalInfoController = this.mainController.getMainController();
            generalInfoController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            SerialSetTableController serialSetTableController = fxmlLoader.getController();
            serialSetTableController.getTable().prefHeightProperty().bind(this.table_SP.heightProperty().multiply(0.895));
        } catch (Exception e){
            System.out.println("Error in setSerialSetTable() - GraphInfoScreenController");
        }
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowGraphInfoController mainControllers) {
        this.mainController = mainControllers;
    }

}
