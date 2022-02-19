package components.tasks;

import components.actions.ActionsController;
import components.adminEnums.AppFxmlPath;
import components.appScreen.AppController;
import components.generalComponents.graphsTable.GraphTableController;
import components.generalComponents.tasksTable.TaskFX;
import components.generalComponents.tasksTable.TasksTableController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

public class PreTaskAdminController extends components.mainControllers.Controllers{
    private TasksAdminController mainController;
    private BooleanProperty isNew;
    private BooleanProperty canNext;
    private BooleanProperty isTextField;
    private BooleanProperty existTaskSelected;
    private static CreateNewTasksController tasksComponentController = null;
    private static Parent tasksParent;
    private GraphTableController graphTableController;
    private Parent graphTableParent;
    private TasksTableController tasksTableController;
    private Parent tasksTableParent;
    private TaskFX selectedTask;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label task_name_label;

    @FXML
    private ComboBox<String> select_CB;

    @FXML
    private StackPane newTask;

    @FXML
    private TextField new_task_name_TA;

    @FXML
    private Label create_message;

    @FXML
    private Label error_message;

    @FXML
    private StackPane graph_table_SP;

    @FXML
    private Button next_btn;

    @FXML
    private StackPane existTask;

    @FXML
    private StackPane task_table_SP;

    @FXML
    private ComboBox<String> howToRunCB;

    @FXML
    private Button create_btn;

    @FXML
    public void initialize() {
        isNew = new SimpleBooleanProperty(true);
        existTaskSelected = new SimpleBooleanProperty(false);
        isTextField = new SimpleBooleanProperty(false);
        canNext = new SimpleBooleanProperty(false);
        select_CB.getItems().addAll("New Task", "Exist Task");
        select_CB.getSelectionModel().select(0);
        howToRunCB.getItems().addAll("From Scratch", "Incremental");
        howToRunCB.getSelectionModel().select(0);
        this.isNew.addListener((a,b,c)->{
            if(this.isNew.getValue()){
                this.newTask.setVisible(true);
                this.existTask.setVisible(false);
            }else{
                this.newTask.setVisible(false);
                this.existTask.setVisible(true);
            }
        });
        this.new_task_name_TA.textProperty().addListener((x,y,z)->{
            if(this.new_task_name_TA.getText().equals("")) {
                this.isTextField.set(false);
            } else{
                this.isTextField.set(true);
            }
        });
        this.next_btn.disableProperty().bind(canNext.not());
        this.create_btn.disableProperty().bind(existTaskSelected.not());
    }

    public TextField getNew_task_name_TA() {
        return new_task_name_TA;
    }

    public void setTables(){
        seTaskTableScreen();
    }

    public void setGraphTableScreen(){
        try{
            if(appController.getGraphTableController() == null)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(AppFxmlPath.DASHBOARD_GRAPH_TABLE.toString());
                fxmlLoader.setLocation(url);
                graphTableParent= fxmlLoader.load(url.openStream());
                appController.setGraphTableParent(this.graphTableParent);
                this.graphTableController = fxmlLoader.getController();
                appController.setGraphTableController(this.graphTableController);
                this.graphTableController.startListRefresher();
                this.graphTableController.setAppController(this.appController);
            } else {
                this.graphTableController = appController.getGraphTableController();
                this.graphTableParent= appController.getGraphTableParent();
            }
            this.appController.setArea(this.graph_table_SP ,appController.getGraphTableParent());
            this.graphTableController.getTable().prefHeightProperty().bind(this.graph_table_SP.heightProperty().multiply(0.925));
            this.graphTableController.getTable().prefWidthProperty().bind(this.graph_table_SP.widthProperty().multiply(0.8));
            this.canNext.bind(this.isTextField.and(this.graphTableController.isSelectedProperty()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void seTaskTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS_TABLE.toString());
            fxmlLoader.setLocation(url);
            tasksTableParent= fxmlLoader.load(url.openStream());
            this.tasksTableController = fxmlLoader.getController();
            this.tasksTableController.setAppController(this.appController);
            this.appController.setArea(this.task_table_SP ,tasksTableParent);
            this.tasksTableController.getTable().prefHeightProperty().bind(this.task_table_SP.heightProperty().multiply(0.925));
            this.tasksTableController.getTable().prefWidthProperty().bind(this.task_table_SP.widthProperty().multiply(0.925));
            this.tasksTableController.startDoneOrCancelTasksRefresher();
            this.tasksTableController.setPreTaskAdminController(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMainController(TasksAdminController mainControllers) {
        this.mainController = mainControllers;
    }

    public TasksAdminController getMainController() {
        return mainController;
    }

    @FXML
    void clickNext(ActionEvent event) {
        String finalUrl = HttpUrl.parse(Constants.CHECK_TASK_NAME).newBuilder().
                addQueryParameter("taskName", new_task_name_TA.getText())
                .build().
                toString();

        Response response = HttpClientUtil.runSync(finalUrl);

        if(response != null && response.code() ==200){
            setNewTasksFxml();
            appController.setArea(this.tasksParent);
            tasksComponentController.getDetails_grid().setVisible(true);
            tasksComponentController.setTableScreen();
            tasksComponentController.setPageScreen(true);
            tasksComponentController.setTaskName(new_task_name_TA.getText());;
            error_message.setText("");
        }else{
            error_message.setText("Task name already exist in system, please choose another name");
        }
    }

    public void setIsOneTargetSelectFromTable(boolean select){
        this.existTaskSelected.set(select);
    }

    public void setSelectedTask(TaskFX selectedTask) {
        this.selectedTask = selectedTask;
    }

    @FXML
    void clickCreate(ActionEvent event) {
        String finalUrl = HttpUrl.parse(Constants.CREATE_COPY_TASK).newBuilder().
                addQueryParameter("taskName", this.selectedTask.getName()).addQueryParameter("runType", this.howToRunCB.getValue())
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(()-> {
                    create_message.setText("Something went wrong. Try again.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(()->{
                    if(response.code()==200){
                        appController.getMenuComponentController().getTaskController().setTasksManagementControllers();
                    }else{
                        create_message.setText("Something went wrong. Try again.");
                    }
                });
            }
        });
    }

    void setNewTasksFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS.toString());
            fxmlLoader.setLocation(url);
            this.tasksParent = fxmlLoader.load(url.openStream());
            this.tasksComponentController= fxmlLoader.getController();
            this.tasksComponentController.setAppController(this.appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void clickSelect(ActionEvent event) {
        if(select_CB.getValue().equals("New Task")){
            isNew.set(true);
        }else{
            isNew.set(false);
        }
    }


    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
