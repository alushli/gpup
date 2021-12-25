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
    private static ShowPathsController showPathsComponentController = null;
    private static Parent showPathsParent;
    private static ShowCirclesController showCirclesComponentController = null;
    private static Parent showCirclesParent;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setShowPathsControllers() {
        if(showPathsComponentController == null) {
            setShowPathsFxml();
        }
        this.appController.setArea(this.appController.getMenuComponentController().getActionController().getShowPathsParent());
    }

    void setShowPathsFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.TARGET_PATHS);
            fxmlLoader.setLocation(url);
            this.showPathsParent = fxmlLoader.load(url.openStream());
            this.showPathsComponentController= fxmlLoader.getController();
            this.showPathsComponentController.setAppController(this.appController);
            this.showPathsComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setShowCirclesControllers() {
        if(showCirclesComponentController == null) {
            setShowCircleFxml();
        }
        this.appController.setArea(this.appController.getMenuComponentController().getActionController().getShowCirclesParent());
    }

    void setShowCircleFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.TARGET_CIRCLES);
            fxmlLoader.setLocation(url);
            this.showCirclesParent = fxmlLoader.load(url.openStream());
            this.showCirclesComponentController= fxmlLoader.getController();
            this.showCirclesComponentController.setAppController(this.appController);
            this.showCirclesComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getShowPathsParent() { return this.showPathsParent; }

    public Parent getShowCirclesParent() { return this.showCirclesParent; }

}
