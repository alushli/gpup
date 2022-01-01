package actions;

import actions.showCircles.ShowCirclesController;
import actions.showPaths.ShowPathsController;
import appScreen.AppController;
import enums.FxmlPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import templates.LoadFileError;

import java.io.IOException;
import java.net.URL;

public class ActionsController extends mainControllers.Controllers {
    private static ShowPathsController showPathsComponentController = null;
    private static Parent showPathsParent;
    private static ShowCirclesController showCirclesComponentController = null;
    private static Parent showCirclesParent;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setShowPathsControllers() {
        if(showPathsComponentController == null) {
            setShowPathsFxml();
        }
        this.setLoadFileHandlingFindPaths();
    }

    public void setLoadFileHandlingFindPaths(){
        this.appController.setArea(getShowPathsParent());
        if(!this.appController.getLoadFile()){
            showPathsComponentController.getDetailsGrid().setVisible(false);
            LoadFileError.setLoadFileError(showPathsComponentController.getDataArea(), this.appController);
        } else {
            LoadFileError.removeLoadFileError(showPathsComponentController.getDataArea());
            showPathsComponentController.getDetailsGrid().setVisible(true);
            showPathsComponentController.setTableScreen();
            showPathsComponentController.setPageScreen();
        }
    }

    void setShowPathsFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_PATHS.toString());
            fxmlLoader.setLocation(url);
            this.showPathsParent = fxmlLoader.load(url.openStream());
            this.showPathsComponentController= fxmlLoader.getController();
            this.showPathsComponentController.setAppController(this.appController);
            this.showPathsComponentController.setMainController(this);
            this.showPathsComponentController.isLightProperty().bind(this.appController.isLightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setArea(StackPane area, Parent data){
        area.getChildren().removeAll();
        area.getChildren().setAll(data);
    }

    public void setShowCirclesControllers() {
        if(showCirclesComponentController == null) {
            setShowCircleFxml();
        }
        this.setLoadFileHandlingFindCircle();
    }

    void setShowCircleFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_CIRCLES.toString());
            fxmlLoader.setLocation(url);
            this.showCirclesParent = fxmlLoader.load(url.openStream());
            this.showCirclesComponentController= fxmlLoader.getController();
            this.showCirclesComponentController.setAppController(this.appController);
            this.showCirclesComponentController.setMainController(this);
            this.showCirclesComponentController.isLightProperty().bind(this.appController.isLightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoadFileHandlingFindCircle(){
        this.appController.setArea(getShowCirclesParent());
        if(!this.appController.getLoadFile()){
            showCirclesComponentController.getDetailsGrid().setVisible(false);
            LoadFileError.setLoadFileError(showCirclesComponentController.getDataArea(), this.appController);
        } else {
            LoadFileError.removeLoadFileError(showCirclesComponentController.getDataArea());
            showCirclesComponentController.getDetailsGrid().setVisible(true);
            showCirclesComponentController.setTableScreen();
            showCirclesComponentController.setPageScreen();
        }
    }

    public Parent getShowPathsParent() { return this.showPathsParent; }

    public Parent getShowCirclesParent() { return this.showCirclesParent; }

}
