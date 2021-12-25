package actions.showPaths;

import actions.ActionsController;
import appScreen.AppController;
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
            URL url = getClass().getResource("detailsScreen/pathsScreen.fxml");
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
        }catch (Exception e){

        }
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../../generalComponents/targetsTable/TargetsTable.fxml");
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
        }catch (Exception e){

        }
    }
}
