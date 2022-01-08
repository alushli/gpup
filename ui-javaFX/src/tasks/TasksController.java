package tasks;

import Enums.SimulationEntryPoint;
import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import enums.FxmlPath;
import enums.StyleSheetsPath;
import generalComponents.targetsTable.TargetsTableController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tasks.runTaskScreen.RunTaskController;
import tasks.runTaskScreen.SelectTargetController;
import tasks.runTaskScreen.SelectTaskScreenController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class TasksController extends mainControllers.Controllers{
    private TargetsTableController targetsTableController;
    private SelectTargetController selectTargetController;
    private SelectTaskScreenController selectTaskScreenController;
    private RunTaskController runTaskController;
    private boolean isWhatIf = false;
    private ArrayList<TargetFXDTO> selectedTargets;
    private StringProperty skin;
    private String whatIfDirection;

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
        this.targetsTableController.setWhatIfHappened(false);
    }

    public ArrayList<TargetFXDTO> getSelectedTargets() {
        return selectedTargets;
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

    public boolean isWhatIf() {
        return isWhatIf;
    }

    public StringProperty skinProperty() {
        return skin;
    }

    @FXML
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
        this.whatIfDirection = "dependsOn";
        setLightListener(this.skin, this.main_screen);
        this.selectedTargets = new ArrayList<>();
    }

    public void setLightListener(StringProperty skin, StackPane stackPane){
        skin.addListener((a,b,c)->{
            if(skin.getValue().equals("Light")){
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_PRINCESS.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.TASK_DARK.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.TASK_PRINCESS.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.TASK_LIGHT.toString());
            }else if(skin.getValue().equals("Dark")){
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_PRINCESS.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.TASK_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.TASK_PRINCESS.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.TASK_DARK.toString());
            } else{
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.TASK_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.TASK_DARK.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MAIN_CSS_PRINCESS.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.TASK_PRINCESS.toString());
            }
        });
    }

    public void updateTaskName(String name){
        this.runTaskController.getTask_name_label().setText(name);
        this.runTaskController.setTaskType(name);
    }

    public void updateSimulationTaskProperties(int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPoint entryPoint, int maxParallel){

        this.runTaskController.setSimulationProperties(getTargetsToRun(this.selectedTargets), processTime, chanceTargetSuccess, chanceTargetWarning, isRandom, entryPoint, maxParallel);
    }

    private Collection<String> getTargetsToRun(ArrayList<TargetFXDTO> targetFXDTOS){
        Collection<String> targets = new HashSet<>();
        for(TargetFXDTO targetFXDTO: targetFXDTOS){
            targets.add(targetFXDTO.getName());
        }
        return targets;
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
            selectTargetController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
            this.selectTargetController.skinListener();
            this.selectTargetController.skinProperty().bind(this.appController.skinProperty());
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
            this.selectTaskScreenController.setMaxThreads();
            selectTaskScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
            this.selectTaskScreenController.skinListener();
            this.selectTaskScreenController.skinProperty().bind(this.appController.skinProperty());
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public void setWhatIfTableDirection(String direction){
        this.whatIfDirection = direction;
    }

    public String getWhatIfDirection() {
        return whatIfDirection;
    }

    public void setRunTaskScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TASK_RUN_TASK.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.main_screen ,fxmlLoader.load(url.openStream()));
            this.runTaskController = fxmlLoader.getController();
            this.runTaskController.setMainController(this);
            this.runTaskController.setAppController(this.appController);
            runTaskController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
            this.runTaskController.skinListener();
            this.runTaskController.skinProperty().bind(this.appController.skinProperty());
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public void setIsOneTargetSelectFromTable(boolean select){
        this.selectTargetController.oneTargetSelectProperty().set(select);
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.setTasksController(this);
            this.targetsTableController.setMaxSelect(this.targetsTableController.getCountTargets());
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));
            this.targetsTableController.skinProperty().bind(this.appController.skinProperty());
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
