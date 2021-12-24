package tasks;

import appScreen.AppController;
import enums.FxmlPath;
import generalInfo.showGraphInfo.ShowGraphInfoController;
import generalInfo.showTargetInfo.ShowTargetInfoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import tasks.compiler.CompilerTaskController;
import tasks.simulation.SimulationTaskController;

import java.io.IOException;
import java.net.URL;

public class TasksController extends mainControllers.Controllers{
    private SimulationTaskController simulationTaskComponentController;
    private CompilerTaskController compilerTaskComponentController;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public Parent setSimulationControllers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.SIMULATION_TASK);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            this.simulationTaskComponentController = fxmlLoader.getController();
            this.simulationTaskComponentController.setAppController(this.appController);
            this.simulationTaskComponentController.setMainController(this);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Parent setCompilerControllers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../" + FxmlPath.COMPILER_TASK);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            this.compilerTaskComponentController = fxmlLoader.getController();
            this.compilerTaskComponentController.setAppController(this.appController);
            this.compilerTaskComponentController.setMainController(this);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
