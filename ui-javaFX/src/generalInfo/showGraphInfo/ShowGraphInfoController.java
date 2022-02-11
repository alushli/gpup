package generalInfo.showGraphInfo;

import appScreen.AppController;
import dtoObjects.GraphDTO;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showGraphInfo.detailsGraphScreen.GraphInfoScreenController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.net.URL;

public class ShowGraphInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;
    private GraphInfoScreenController graphInfoScreenController;
    private TargetsTableController targetsTableController;
    private StringProperty skin;

    @FXML
    private StackPane main_screen;

    @FXML
    private StackPane data_area;

    @FXML
    private GridPane details_grid;

    @FXML
    private VBox table_VB;

    @FXML
    private StackPane table_SP;

    @FXML
    private StackPane page_SP;

    public GridPane getDetailsGrid() { return details_grid; }

    public StackPane getDataArea() {
        return data_area;
    }

    @FXML
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
    }

    public void skinListener(){
        this.mainController.skinListener(this.skin, this.main_screen);
    }

    public StringProperty skinProperty() {
        return skin;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }

    public GeneralInfoController getMainController() {
        return mainController;
    }

    public void setPageScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_GRAPH_INFO_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.graphInfoScreenController = fxmlLoader.getController();
            this.graphInfoScreenController.setMainController(this);
            this.graphInfoScreenController.setAppController(this.appController);
            this.graphInfoScreenController.skinListener();
            this.graphInfoScreenController.skinProperty().bind(this.appController.skinProperty());
            if(this.appController.hasSerialSets())
                this.graphInfoScreenController.setSerialSetTable();
            else
                this.graphInfoScreenController.setNoSerialSet();
            setPageLabels();
        } catch (Exception e){
        }
    }

     public void skinListener(StringProperty skin, StackPane stackPane){
        this.mainController.skinListener(skin, stackPane);
    }

    private void setPageLabels(){
        GraphDTO graphDTO = this.appController.getGraphInfo();


        this.graphInfoScreenController.getGraph_name_label().setText(graphDTO.getGraphName());
        this.graphInfoScreenController.getGraph_work_dir_label().setText(graphDTO.getWorkingDirectory());
        this.graphInfoScreenController.getTarget_num_label().setText(String.valueOf(graphDTO.getCountRoots() + graphDTO.getCountMiddles() + graphDTO.getCountLeaves() + graphDTO.getCountIndependents()));
        this.graphInfoScreenController.getRoot_num_label().setText(String.valueOf(graphDTO.getCountRoots()));
        this.graphInfoScreenController.getMid_num_label().setText(String.valueOf(graphDTO.getCountMiddles()));
        this.graphInfoScreenController.getLeaf_num_label().setText(String.valueOf(graphDTO.getCountLeaves()));
        this.graphInfoScreenController.getInd_num_label().setText(String.valueOf(graphDTO.getCountIndependents()));
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.965));
            this.targetsTableController.setSelectDisable();
            this.targetsTableController.skinProperty().bind(this.appController.skinProperty());
        }catch (Exception e){

        }
    }
}
