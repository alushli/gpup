package actions;

import actions.showCircles.ShowCirclesController;
import actions.showPaths.ShowPathsController;
import appScreen.AppController;
import enums.FxmlPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

public class ActionsController extends mainControllers.Controllers {
    private ShowPathsController showPathsComponentController;
    private ShowCirclesController showCirclesComponentController;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public Parent setShowPathsControllers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.TARGET_PATHS);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            this.showPathsComponentController = fxmlLoader.getController();
            this.showPathsComponentController.setAppController(this.appController);
            this.showPathsComponentController.setMainController(this);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Parent setShowCirclesControllers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.TARGET_CIRCLES);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            this.showCirclesComponentController = fxmlLoader.getController();
            this.showCirclesComponentController.setAppController(this.appController);
            this.showCirclesComponentController.setMainController(this);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
