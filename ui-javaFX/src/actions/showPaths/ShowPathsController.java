package actions.showPaths;

import actions.ActionsController;
import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import enums.StyleSheetsPath;
import generalComponents.targetsTable.TargetsTableController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private BooleanProperty isLight;

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
        this.isLight = new SimpleBooleanProperty(true);
        this.isLight.addListener((a,b,c)->{
            if(this.isLight.getValue()){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.ACTIONS_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.ACTIONS_LIGHT.toString());
            }else{
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.ACTIONS_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.ACTIONS_DARK.toString());
            }
        });
    }

    @FXML
    void clickDeselectAll(ActionEvent event) {
        this.targetsTableController.deselectAll();
    }

    public BooleanProperty isLightProperty() {
        return isLight;
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
            this.pathsScreenController.isLightProperty().bind(this.appController.isLightProperty());
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
            this.targetsTableController.isLightProperty().bind(this.appController.isLightProperty());
        }catch (Exception e){
            System.out.println("Error in setTableScreen() - showPathController");
        }
    }
}
