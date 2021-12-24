package generalInfo;

import appScreen.AppController;
import enums.FxmlPath;
import enums.ScreenTypes;
import generalInfo.showGraphInfo.ShowGraphInfoController;
import generalInfo.showTargetInfo.ShowTargetInfoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

public class GeneralInfoController extends mainControllers.Controllers {
    private ShowTargetInfoController showTargetInfoComponentController;
    private ShowGraphInfoController showGraphInfoComponentController;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public Parent setTargetInfoControllers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.TARGET_INFO);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            this.showTargetInfoComponentController = fxmlLoader.getController();
            this.showTargetInfoComponentController.setAppController(this.appController);
            this.showTargetInfoComponentController.setMainController(this);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Parent setGraphInfoControllers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.GRAPH_INFO);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            this.showGraphInfoComponentController = fxmlLoader.getController();
            this.showGraphInfoComponentController.setAppController(this.appController);
            this.showGraphInfoComponentController.setMainController(this);
            this.showGraphInfoComponentController.setLoadFileHandling();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
