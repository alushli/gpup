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
    private static ShowTargetInfoController showTargetInfoComponentController = null;
    private static Parent showTargetInfoParent;
    private static ShowGraphInfoController showGraphInfoComponentController = null;
    private static Parent showGraphInfoParent;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setTargetInfoControllers() {
        if(showTargetInfoComponentController == null) {
            setTargetInfoFxml();
        }
    }

    void setTargetInfoFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.TARGET_INFO);
            fxmlLoader.setLocation(url);
            this.showTargetInfoParent = fxmlLoader.load(url.openStream());
            this.showTargetInfoComponentController= fxmlLoader.getController();
            this.showTargetInfoComponentController.setAppController(this.appController);
            this.showTargetInfoComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGraphInfoControllers() {
        if(showGraphInfoComponentController == null) {
            setGraphInfoFxml();
        }
        this.showGraphInfoComponentController.setLoadFileHandling();
    }

    void setGraphInfoFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.GRAPH_INFO);
            fxmlLoader.setLocation(url);
            this.showGraphInfoParent = fxmlLoader.load(url.openStream());
            this.showGraphInfoComponentController= fxmlLoader.getController();
            this.showGraphInfoComponentController.setAppController(this.appController);
            this.showGraphInfoComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getShowTargetInfoParent() { return this.showTargetInfoParent; }

    public Parent getShowGraphInfoParent() { return this.showGraphInfoParent; }

}
