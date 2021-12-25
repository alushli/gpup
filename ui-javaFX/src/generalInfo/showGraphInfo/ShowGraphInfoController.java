package generalInfo.showGraphInfo;

import appScreen.AppController;
import generalInfo.GeneralInfoController;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class ShowGraphInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;


    @FXML
    private StackPane data_area;

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
}
