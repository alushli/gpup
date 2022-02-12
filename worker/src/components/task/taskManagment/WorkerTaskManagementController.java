package components.task.taskManagment;

import components.appScreen.AppController;
import components.generalComponents.targetsTask.TargetsTaskController;
import components.generalComponents.workerTasks.WorkerTasksController;
import components.menu.WorkerMenuController;
import components.workerEnums.AppFxmlPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class WorkerTaskManagementController extends components.workerMainControllers.workerControllers{
    private TargetsTaskController taskTargetTableController;
    private Parent taskTargetTableParent;
    private WorkerTasksController workerTasksController;
    private Parent workerTasksParent;
    private WorkerMenuController mainController;

    @FXML
    private StackPane main_screen;

    @FXML
    private TextArea log_TA;

    @FXML
    private Label credit_label;

    @FXML
    private Label threads_occupied_count;

    @FXML
    private Label threads_sum_count;

    @FXML
    private StackPane targets_table_SP;

    @FXML
    private StackPane task_table_SP;

    public void setTables(){
        setTargetsTable();
        this.appController.setArea(this.targets_table_SP, getTaskTargetTableParent());
        setTaskTable();
        this.appController.setArea(this.task_table_SP, getWorkerTasksParent());
    }

    public Parent getTaskTargetTableParent() {
        return taskTargetTableParent;
    }

    public Parent getWorkerTasksParent() {
        return workerTasksParent;
    }

    private void setTargetsTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TARGETS_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.taskTargetTableParent = fxmlLoader.load(url.openStream());
            this.taskTargetTableController = fxmlLoader.getController();
            this.taskTargetTableController.setAppController(this.appController);
            this.taskTargetTableController.setMainController(this);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTaskTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.WORK_TASK_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.workerTasksParent = fxmlLoader.load(url.openStream());
            this.workerTasksController = fxmlLoader.getController();
            this.workerTasksController.setAppController(this.appController);
            this.workerTasksController.setMainController(this);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainController(WorkerMenuController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
