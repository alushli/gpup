package generalInfo.showTargetInfo;

import appScreen.AppController;
import generalInfo.GeneralInfoController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowTargetInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }
}
