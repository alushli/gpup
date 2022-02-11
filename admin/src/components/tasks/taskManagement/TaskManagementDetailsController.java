package components.tasks.taskManagement;

import components.adminEnums.AppFxmlPath;
import components.appScreen.AppController;
import components.generalComponents.taskTargetTable.TaskTargetTableController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class TaskManagementDetailsController extends components.mainControllers.Controllers{
    private TaskManagementController mainController;
    private TaskTargetTableController taskTargetTableController;
    private Parent waitingTaskManagementDetailsParent;
    private Parent processTaskManagementDetailsParent;
    private Parent doneTaskManagementDetailsParent;

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

    }

    @FXML
    void clickResume(ActionEvent event) {

    }

    @FXML
    void clickStart(ActionEvent event) {

    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

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
            this.taskTargetTableController = fxmlLoader.getController();
            this.taskTargetTableController.setAppController(this.appController);
            this.taskTargetTableController.setMainController(this);
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setMainController(TaskManagementController mainControllers) {
        this.mainController = mainControllers;
    }
}
