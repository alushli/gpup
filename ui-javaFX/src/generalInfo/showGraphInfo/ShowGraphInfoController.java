package generalInfo.showGraphInfo;

import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showGraphInfo.detailsGraphScreen.GraphInfoScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;

public class ShowGraphInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;

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
            GraphInfoScreenController graphInfoScreenController = fxmlLoader.getController();
            graphInfoScreenController.setMainController(this);
            graphInfoScreenController.setAppController(this.appController);
            graphInfoScreenController.setSerialSetTable();
            graphInfoScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
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
            targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.965));
        }catch (Exception e){

        }
    }
}
