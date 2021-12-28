package actions.showCircles;

import actions.ActionsController;
import actions.showCircles.detailsCircleScreen.CircleScreenController;
import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;

public class ShowCirclesController extends mainControllers.Controllers{
    private ActionsController mainController;//means - actionController
    private TargetsTableController targetsTableController;
    private CircleScreenController circleScreenController;
    private IntegerProperty curSelectedCount;

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

    public StackPane getDataArea() { return data_area; }

    public GridPane getDetailsGrid() { return details_grid; }

    public void setPageScreen(){
        try{
            this.curSelectedCount = new SimpleIntegerProperty();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_CIRCLE_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.circleScreenController = fxmlLoader.getController();
            this.circleScreenController.setMainController(this);
            this.circleScreenController.setAppController(this.appController);
            this.circleScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
            setTargetsLabel();
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showCircleController");
        }
    }

    private void setTargetsLabel(){
        this.curSelectedCount.bind(this.targetsTableController.selectedCounterProperty());
        this.curSelectedCount.addListener((a,b,c)->{
            if(curSelectedCount.getValue() == 0){
                this.circleScreenController.getFind_btn().setDisable(true);
                this.circleScreenController.getTarget_label().setText("");
            } else {
                this.circleScreenController.getFind_btn().setDisable(false);
                this.circleScreenController.getTarget_label().setText(this.targetsTableController.getCurSelected().get(0).getName());
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
            this.targetsTableController.setMaxSelect(1);
        }catch (Exception e){
            System.out.println("Error in setTableScreen() - showCircleController");
        }
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ActionsController mainControllers) {
        this.mainController = mainControllers;
    }
}
