package components.task.taskManagment;

import components.appScreen.AppController;
import components.generalComponents.targetsTask.TargetsTaskController;
import components.generalComponents.targetsTask.TargetsTaskFX;
import components.generalComponents.workerTasks.WorkerTasksController;
import components.generalComponents.workerTasks.WorkerTasksFX;
import components.menu.WorkerMenuController;
import components.workerEnums.AppFxmlPath;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WorkerTaskManagementController extends components.workerMainControllers.workerControllers{
    private TargetsTaskController taskTargetTableController;
    private Parent taskTargetTableParent;
    private WorkerTasksController workerTasksController;
    private Parent workerTasksParent;
    private WorkerMenuController mainController;

    private Timer timer;
    private TimerTask listRefresher;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty(true);
    private IntegerProperty totalTasks = new SimpleIntegerProperty(0);

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
            this.workerTasksController.updateTable(this.appController.getWorkerEngine().getAllTasksByMe());
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

    public void startListRefresher() {
        listRefresher = new WorkerTasksManagementRefresher(
                autoUpdate,
                this::updateTaskList,
                this.appController.getWorkerEngine(),
                this::updateTargetList,
                this::updateCredits,
                this::updateOcThreads,
                this::updateSumThreads,
                this::updateLogs);
        timer = new Timer();
        timer.schedule(listRefresher, 1000, 1000);
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate.set(autoUpdate);
    }

    private void updateTaskList(Collection<WorkerTasksFX> tasks){
        this.workerTasksController.updateTable(tasks);
    }

    private void updateTargetList(Collection<TargetsTaskFX> targets){
        this.taskTargetTableController.updateTable(targets);
    }

    private void updateCredits(int credits){
        Platform.runLater(()->{
            this.credit_label.setText(String.valueOf(credits));
        });
    }

    private void updateOcThreads(int amount){
        Platform.runLater(()->{
            this.threads_occupied_count.setText(String.valueOf(amount));
        });
    }

    private void updateSumThreads(int amount){
        Platform.runLater(()->{
            this.threads_sum_count.setText(String.valueOf(amount));
        });
    }

    private void updateLogs(LinkedHashMap<String, List<String>> logs){
        Platform.runLater(()->{
            this.log_TA.setText("");
            for (Map.Entry<String, List<String>> entry : logs.entrySet()){
                this.log_TA.appendText(entry.getKey() +":"+ '\n');
                for (String log : entry.getValue()){
                    this.log_TA.appendText(log + '\n');
                }
            }
        });
    }
}
