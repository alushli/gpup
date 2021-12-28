package generalInfo.showTargetInfo;

import actions.showPaths.detailsPathsScreen.PathsScreenController;
import appScreen.AppController;
import enums.FxmlPath;
import generalComponents.targetsTable.TargetsTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showTargetInfo.detailsTargetScreen.TargetInfoScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;

public class ShowTargetInfoController extends mainControllers.Controllers{
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

    public StackPane getDataArea() { return data_area; }

    public GridPane getDetailsGrid() { return details_grid; }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }

    public void setPageScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_TARGET_INFO_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            TargetInfoScreenController targetInfoScreenController = fxmlLoader.getController();
            targetInfoScreenController.setMainController(this);
            targetInfoScreenController.setAppController(this.appController);
            targetInfoScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
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





    public void setTable(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(fxmlLoader.load(url.openStream()));
        }catch (Exception e){
            System.out.println("problem with table");
        }

    }
}
