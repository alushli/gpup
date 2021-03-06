package generalInfo.showGraphInfo.detailsGraphScreen;

import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.serialSetTable.SerialSetTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showGraphInfo.ShowGraphInfoController;
import generalInfo.showGraphInfo.detailsGraphScreen.exportGraphScreen.ExportGraphScreenController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

public class GraphInfoScreenController extends mainControllers.Controllers{
    private ShowGraphInfoController mainController;
    private String fullPathExport;
    private Stage popupWindow;
    private StringProperty skin;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label graph_name_label;

    @FXML
    private Label graph_work_dir_label;

    @FXML
    private Button export_btn;

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
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
    }

    public StringProperty skinProperty() {
        return skin;
    }

    public void setFullPathExport(String fullPathExport) {
        this.fullPathExport = fullPathExport;
    }

    @FXML
    void clickExport(ActionEvent event){
        setPopup();
    }

    public void skinListener(){
        this.mainController.skinListener(this.skin, this.fall_screen_SP);
    }

    public void skinListener(StringProperty skin, StackPane stackPane){
        this.mainController.skinListener(skin, stackPane);
    }

    public ShowGraphInfoController getMainController() {
        return mainController;
    }

    private void setPopup(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlPath.EXPORT_GRAPH_POPUP.toString()));
            Parent popup = (Parent) loader.load();
            ExportGraphScreenController exportGraphScreenController = loader.getController();
            exportGraphScreenController.setAppController(this.appController);
            exportGraphScreenController.setMainController(this);
            exportGraphScreenController.skinListener();
            exportGraphScreenController.skinProperty().bind(this.appController.skinProperty());
            Scene secondScene = new Scene(popup, 520, 500);
            this.popupWindow = new Stage();
            this.popupWindow.setResizable(false);
            this.popupWindow.setTitle("Export graph");
            this.popupWindow.setAlwaysOnTop(true);
            this.popupWindow.setScene(secondScene);
            this.popupWindow.setX(this.appController.getPrimaryStage().getX() + 200);
            this.popupWindow.setY(this.appController.getPrimaryStage().getY() + 100);
            this.popupWindow.show();
        }catch (Exception e){
        }

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

    public Label getGraph_work_dir_label() {
        return graph_work_dir_label;
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

    public void setSerialSetTable(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.SERIAL_SET_TABLE.toString());
            fxmlLoader.setLocation(url);
            GeneralInfoController generalInfoController = this.mainController.getMainController();
            generalInfoController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            SerialSetTableController serialSetTableController = fxmlLoader.getController();
            serialSetTableController.getTable().prefHeightProperty().bind(this.table_SP.heightProperty().multiply(0.895));
            serialSetTableController.skinProperty().bind(this.appController.skinProperty());

        } catch (Exception e){
            System.out.println("Error in setSerialSetTable() - GraphInfoScreenController");
        }
    }

    public void setNoSerialSet(){
        Label label = new Label();
        label.setText("There are no serial sets for this graph.");
        this.table_SP.getChildren().add(label);
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
