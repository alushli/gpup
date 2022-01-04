package tasks;

import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showTargetInfo.detailsTargetScreen.TargetInfoScreenController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tasks.runTaskScreen.SelectTargetController;
import tasks.runTaskScreen.SelectTaskScreenController;

import java.net.URL;
import java.util.ArrayList;

public class TasksController extends mainControllers.Controllers{
    private TargetsTableController targetsTableController;
    private SelectTargetController selectTargetController;
    private SelectTaskScreenController selectTaskScreenController;
    private boolean isWhatIf = false;
    private ArrayList<TargetFXDTO> selectedTargets;

    @FXML
    private Button select_all_btn;

    @FXML
    private Button deselect_all_btn;


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

    @FXML
    void clickDeselectAll(ActionEvent event) {
        this.targetsTableController.deselectAll();
    }

    @FXML
    void clickSelectAll(ActionEvent event) {
        this.targetsTableController.SelectAll();
    }

    public StackPane getData_area() {
        return data_area;
    }

    public GridPane getDetails_grid() {
        return details_grid;
    }

    public void setWhatIf(boolean whatIf) {
        isWhatIf = whatIf;
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setSelectedTargets(ArrayList<TargetFXDTO> selectedTargets) {
        this.selectedTargets = selectedTargets;
    }


    public void setPageScreen(boolean firstTime){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TASK_SELECT_TARGET.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.selectTargetController = fxmlLoader.getController();
            this.selectTargetController.setMainController(this);
            this.selectTargetController.setAppController(this.appController);
            this.selectTargetController.getCount_selected_targets().textProperty().bind(this.getTargetsTableController().countSelectedTargetsAsStringProperty());
            selectTargetController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
            if(!firstTime){
                this.targetsTableController.setSelectedTargets(this.selectedTargets);
               this.selectTargetController.getWhat_if_CB().setSelected(this.isWhatIf);
            }
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public SelectTargetController getSelectTargetController() {
        return selectTargetController;
    }

    public TargetsTableController getTargetsTableController() {
        return targetsTableController;
    }

    public void setSelectTaskScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TASK_SELECT_TASK.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.selectTaskScreenController = fxmlLoader.getController();
            this.selectTaskScreenController.setMainController(this);
            this.selectTaskScreenController.setAppController(this.appController);
            selectTaskScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.setMaxSelect(this.targetsTableController.getCountTargets());
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));

            //this.targetsTableController.isLightProperty().bind(this.appController.isLightProperty());
        }catch (Exception e){

        }
    }

    public void setTableButtonDisable(){
        this.deselect_all_btn.setDisable(true);
        this.select_all_btn.setDisable(true);
    }

    public void setTableButtonEnable(){
        this.deselect_all_btn.setDisable(false);
        this.select_all_btn.setDisable(false);
    }


}
