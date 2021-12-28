package actions.showPaths;

import actions.ActionsController;
import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.net.URL;

public class ShowPathsController extends mainControllers.Controllers{
    private ActionsController mainController;

    @FXML
    private StackPane data_area;

    @FXML
    private StackPane table_SP;

    @FXML
    private StackPane page_SP;

    @FXML
    private GridPane details_grid;

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
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_PATH_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            PathsScreenController pathsScreenController = fxmlLoader.getController();
            pathsScreenController.setMainController(this);
            pathsScreenController.setAppController(this.appController);
            pathsScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
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
}
