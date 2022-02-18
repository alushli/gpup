package components.tasks.taskManagement;

import com.google.gson.Gson;
import components.adminEnums.AppFxmlPath;
import components.appScreen.AppController;
import components.generalComponents.graphsTable.GraphFx;
import components.generalComponents.graphsTable.GraphListRefresher;
import components.generalComponents.taskTargetTable.TaskDoneTargetTableController;
import components.generalComponents.taskTargetTable.TaskProcessTargetTableController;
import components.generalComponents.taskTargetTable.TaskWaitingTargetTableController;
import components.generalComponents.tasksTable.TasksTableController;
import dtoObjects.TargetRunFX;
import dtoObjects.TaskRuntimeForAdmin;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import newEnums.TargetAllStatuses;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskManagementDetailsController extends components.mainControllers.Controllers implements Closeable {
    private TaskManagementController mainController;
    private TasksTableController taskTableController;
    private TaskWaitingTargetTableController taskWaitingTargetTableController;
    private TaskProcessTargetTableController taskProcessTargetTableController;
    private TaskDoneTargetTableController taskDoneTargetTableController;
    private Parent waitingTaskManagementDetailsParent;
    private Parent processTaskManagementDetailsParent;
    private Parent doneTaskManagementDetailsParent;
    private String taskName;

    private Timer timer;
    private TimerTask listRefresher;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty(true);
    private IntegerProperty totalTargets = new SimpleIntegerProperty(0);
    private IntegerProperty targetsDone = new SimpleIntegerProperty(0);

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void close() {
        taskWaitingTargetTableController.getTable().getItems().clear();
        taskProcessTargetTableController.getTable().getItems().clear();
        taskDoneTargetTableController.getTable().getItems().clear();
        totalTargets.set(0);
        if (listRefresher != null && timer != null) {
            listRefresher.cancel();
            timer.cancel();
        }
    }

    public void setTaskTableController(TasksTableController taskTableController) {
        this.taskTableController = taskTableController;
    }

    @FXML
    private StackPane main_screen;

    @FXML
    private TextArea log_TA;

    @FXML
    private Label task_name_label;

    @FXML
    private Label graph_name_label;

    @FXML
    private Label workers_num_label;

    @FXML
    private Button start_btn;

    @FXML
    private Button pause_btn;

    @FXML
    private Button resume_btn;

    @FXML
    private Button stop_btn;

    @FXML
    private Label task_status_label;

    @FXML
    private ProgressBar progress_bar;

    @FXML
    private Label finish_targets_count;

    @FXML
    private Label all_targets_to_run_count;

    @FXML
    private StackPane waiting_table_SP;

    @FXML
    private StackPane process_table_SP;

    @FXML
    private StackPane done_table_SP;

    @FXML
    public void initialize(){

    }

    @FXML
    void clickPause(ActionEvent event) {
        String finalUrl = HttpUrl.parse(Constants.TASK_ACTION).newBuilder().
                addQueryParameter("taskName", taskName).addQueryParameter("action", "pause")
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on pause task.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(()->{
                    if(response.code()==200){
                        System.out.println("Task paused!");
                        resume_btn.disableProperty().set(false);
                        pause_btn.disableProperty().set(true);
                    }else{
                        System.out.println("Something went wrong!!!");
                    }
                });
            }
        });
    }

    @FXML
    void clickResume(ActionEvent event) {
        String finalUrl = HttpUrl.parse(Constants.TASK_ACTION).newBuilder().
                addQueryParameter("taskName", taskName).addQueryParameter("action", "resume")
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on resume task.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(()->{
                    if(response.code()==200){
                        System.out.println("Task started!");
                        resume_btn.disableProperty().set(true);
                        pause_btn.disableProperty().set(false);
                        autoUpdate.set(true);
                    }else{
                        System.out.println("Something went wrong!!!");
                    }
                });
            }
        });
    }

    @FXML
    void clickStart(ActionEvent event) {
        String finalUrl = HttpUrl.parse(Constants.TASK_ACTION).newBuilder().
                addQueryParameter("taskName", taskName).addQueryParameter("action", "start")
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on start task.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(()->{
                    if(response.code()==200){
                        System.out.println("Task started!");

                        start_btn.disableProperty().set(true);
                        pause_btn.disableProperty().set(false);
                    }else{
                        System.out.println("Something went wrong!!!");
                    }
                });
            }
        });
    }

    @FXML
    void clickStop(ActionEvent event) {
        String finalUrl = HttpUrl.parse(Constants.TASK_ACTION).newBuilder().
                addQueryParameter("taskName", taskName).addQueryParameter("action", "cancel")
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on cancel task.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(()->{
                    if(response.code()==200){
                        System.out.println("Task canceled!");
                        setAllButtonsDisable();
                    }else{
                        System.out.println("Something went wrong!!!");
                    }
                });
            }
        });
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    private StringProperty taskStatus = new SimpleStringProperty();

    public void setScreenStaticDetails(String graphName, String taskName){
        this.graph_name_label.setText(graphName);
        this.task_name_label.setText(taskName);
    }

    public void setTables(){
        setWaitingTable();
        this.appController.setArea(this.waiting_table_SP, getWaitingTaskManagementDetailsParent());
        setProcessTable();
        this.appController.setArea(this.process_table_SP, getProcessTaskManagementDetailsParent());
        setDoneTable();
        this.appController.setArea(this.done_table_SP, getDoneTaskManagementDetailsParent());
    }

    public void setWaitingTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS_TARGET_WAITING_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.waitingTaskManagementDetailsParent = fxmlLoader.load(url.openStream());
            this.taskWaitingTargetTableController = fxmlLoader.getController();
            this.taskWaitingTargetTableController.setAppController(this.appController);
            this.taskWaitingTargetTableController.setMainController(this);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getWaitingTaskManagementDetailsParent() {
        return waitingTaskManagementDetailsParent;
    }

    public Parent getProcessTaskManagementDetailsParent() {
        return processTaskManagementDetailsParent;
    }

    public Parent getDoneTaskManagementDetailsParent() {
        return doneTaskManagementDetailsParent;
    }

    public void setProcessTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS_TARGET_PROCESS_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.processTaskManagementDetailsParent = fxmlLoader.load(url.openStream());
            this.taskProcessTargetTableController = fxmlLoader.getController();
            this.taskProcessTargetTableController.setAppController(this.appController);
            this.taskProcessTargetTableController.setMainController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setDoneTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS_TARGET_DONE_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.doneTaskManagementDetailsParent = fxmlLoader.load(url.openStream());
            this.taskDoneTargetTableController = fxmlLoader.getController();
            this.taskDoneTargetTableController.setAppController(this.appController);
            this.taskDoneTargetTableController.setMainController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setMainController(TaskManagementController mainControllers) {
        this.mainController = mainControllers;
    }

    public void startListRefresher() {
        listRefresher = new TaskManagmentRefresher(
                autoUpdate,
                this::updateTables,
                taskName,
                targetsDone,
                this::updateWorkersLabel,
                this::setStatus,
                this::setProgressBar,
                this::setAllTargets,
                this::setFinishTargets,
                this::updateLogs);
        timer = new Timer();
        timer.schedule(listRefresher, 1000, 1000);
    }

    private void updateTables(List<TargetRunFX> targets) {
        Platform.runLater(() -> {
            taskWaitingTargetTableController.getTable().getItems().clear();
            taskProcessTargetTableController.getTable().getItems().clear();
            taskDoneTargetTableController.getTable().getItems().clear();
            for (TargetRunFX targetRunFX : targets){
                if(targetRunFX.getRunStatus().equals(TargetAllStatuses.FROZEN.toString()) || targetRunFX.getRunStatus().equals(TargetAllStatuses.WAITING.toString())){
                    taskWaitingTargetTableController.getTable().getItems().add(targetRunFX);
                }else if(targetRunFX.getRunStatus().equals(TargetAllStatuses.IN_PROCESS.toString())){
                    taskProcessTargetTableController.getTable().getItems().add(targetRunFX);
                }else{
                    taskDoneTargetTableController.getTable().getItems().add(targetRunFX);
                }
            }
            totalTargets.set(targets.size());
        });
    }

    private void updateWorkersLabel(Integer workers){
        Platform.runLater(()->{
            this.workers_num_label.setText(String.valueOf(workers));
        });
    }

    public void setAllButtonsDisable(){
        this.start_btn.setDisable(true);
        this.pause_btn.setDisable(true);
        this.resume_btn.setDisable(true);
        this.stop_btn.setDisable(true);
    }

    public void setStatus(String status){
        this.taskStatus.set(status);
        this.taskTableController.getTaskFXSelected().setStatus(status);
        Platform.runLater(()->{
            this.task_status_label.setText(status);
            if(status.equalsIgnoreCase("done")){
                setAllButtonsDisable();
            }
        });
    }

    public String getTaskStatus() {
        return taskStatus.get();
    }

    public StringProperty taskStatusProperty() {
        return taskStatus;
    }

    public Button getStart_btn() {
        return start_btn;
    }

    public Button getPause_btn() {
        return pause_btn;
    }

    public Button getResume_btn() {
        return resume_btn;
    }

    public void setProgressBar(float progressBar){
        Platform.runLater(()->{
            this.progress_bar.setProgress(progressBar);
        });
    }

    public void setAllTargets(int allTargets){
        Platform.runLater(()->{
            this.all_targets_to_run_count.setText(String.valueOf(allTargets));
        });
    }

    public void setFinishTargets(int targetFinish){
        Platform.runLater(()->{
            this.finish_targets_count.setText(String.valueOf(targetFinish));
        });
    }

    public void updateLogs(List<String> logs){
        Platform.runLater(()->{
            this.log_TA.setText("");
            for(String str:logs){
                this.log_TA.appendText(str + '\n');
            }
        });
    }

}
