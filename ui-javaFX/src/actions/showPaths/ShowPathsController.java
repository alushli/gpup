package actions.showPaths;

import actions.ActionsController;
import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.net.URL;

public class ShowPathsController extends mainControllers.Controllers{
    private ActionsController mainController;
    private TargetsTableController targetsTableController;
    private IntegerProperty curSelectedCount;
    private PathsScreenController pathsScreenController;
    private StringProperty skin;

    @FXML
    private StackPane main_screen;

    @FXML
    private StackPane data_area;

    @FXML
    private StackPane table_SP;

    @FXML
    private StackPane page_SP;

    @FXML
    private GridPane details_grid;

    @FXML
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
    }

    public void skinListener(){
        this.mainController.skinListener(this.skin, this.main_screen);
    }

    @FXML
    void clickDeselectAll(ActionEvent event) {
        this.targetsTableController.deselectAll();
    }

    public StringProperty skinProperty() {
        return skin;
    }

    public StackPane getDataArea() { return data_area; }

    public GridPane getDetailsGrid() { return details_grid; }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(ActionsController mainControllers) {
        this.mainController = mainControllers;
    }

    public ActionsController getMainController() {
        return mainController;
    }

    public void setPageScreen(){
        try{
            this.curSelectedCount = new SimpleIntegerProperty();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_PATH_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.pathsScreenController = fxmlLoader.getController();
            this.pathsScreenController.setMainController(this);
            this.pathsScreenController.setAppController(this.appController);
            this.pathsScreenController.skinProperty().bind(this.appController.skinProperty());
            this.pathsScreenController.skinListener();
            this.pathsScreenController.isAnimationProperty().bind(this.appController.isAnimationProperty());
            setTargetsLabels();
            pathsScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    private void setTargetsLabels(){
        this.curSelectedCount.bind(this.targetsTableController.selectedCounterProperty());
        this.curSelectedCount.addListener((a,b,c)->{
            if(curSelectedCount.getValue() == 0){
                this.pathsScreenController.getFind_btn().setDisable(true);
                this.pathsScreenController.getTarget1_label().setText("");
                this.pathsScreenController.getTarget2_label().setText("");
                this.pathsScreenController.getPaths_TA().setText("");
            } else if(curSelectedCount.getValue() == 1){
                this.pathsScreenController.getFind_btn().setDisable(true);
                this.pathsScreenController.getTarget1_label().setText(this.targetsTableController.getCurSelected().get(0).getName());
                this.pathsScreenController.getTarget2_label().setText("");
                this.pathsScreenController.getPaths_TA().setText("");
            } else{
                this.pathsScreenController.getFind_btn().setDisable(false);
                this.pathsScreenController.getTarget1_label().setText(this.targetsTableController.getCurSelected().get(0).getName());
                this.pathsScreenController.getTarget2_label().setText(this.targetsTableController.getCurSelected().get(1).getName());
            }
        });
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));
            this.targetsTableController.setMaxSelect(2);
            this.targetsTableController.skinProperty().bind(this.appController.skinProperty());
        }catch (Exception e){
            System.out.println("Error in setTableScreen() - showPathController");
        }
    }
}
