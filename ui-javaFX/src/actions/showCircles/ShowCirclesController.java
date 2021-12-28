package actions.showCircles;

import actions.ActionsController;
import actions.showCircles.detailsCircleScreen.CircleScreenController;
import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;

public class ShowCirclesController extends mainControllers.Controllers{
    private ActionsController mainController;//means - actionController

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
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_CIRCLE_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            CircleScreenController circleScreenController = fxmlLoader.getController();
            circleScreenController.setMainController(this);
            circleScreenController.setAppController(this.appController);
            circleScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
        } catch (Exception e){
        }
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            TargetsTableController targetsTableController = fxmlLoader.getController();
            targetsTableController.setAppController(this.appController);
            targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));
        }catch (Exception e){

        }
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ActionsController mainControllers) {
        this.mainController = mainControllers;
    }
}
