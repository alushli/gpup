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
    private static SimulationTaskController simulationTaskComponentController = null;
    private static Parent simulationTaskParent;
    private static CompilerTaskController compilerTaskComponentController = null;
    private static Parent compilerTaskParent;

    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setSimulationControllers() {
        if(simulationTaskComponentController == null) {
            setSimulationTaskFxml();
        }
        this.appController.setArea(this.appController.getMenuComponentController().getTasksController().getSimulationTaskParent());
    }

    void setSimulationTaskFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.SIMULATION_TASK.toString());
            fxmlLoader.setLocation(url);
            this.simulationTaskParent = fxmlLoader.load(url.openStream());
            this.simulationTaskComponentController= fxmlLoader.getController();
            this.simulationTaskComponentController.setAppController(this.appController);
            this.simulationTaskComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCompilerControllers() {
        if(compilerTaskComponentController == null) {
            setCompilerTaskFxml();
        }
        this.appController.setArea(this.appController.getMenuComponentController().getTasksController().getCompilerTaskParent());
    }

    void setCompilerTaskFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.COMPILER_TASK.toString());
            fxmlLoader.setLocation(url);
            this.compilerTaskParent = fxmlLoader.load(url.openStream());
            this.compilerTaskComponentController= fxmlLoader.getController();
            this.compilerTaskComponentController.setAppController(this.appController);
            this.compilerTaskComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getSimulationTaskParent() { return this.simulationTaskParent; }

    public Parent getCompilerTaskParent() { return this.compilerTaskParent; }
}
