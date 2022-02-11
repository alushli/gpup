package components.tasks.taskManagement;

import components.adminEnums.AppFxmlPath;
import components.appScreen.AppController;
import components.generalComponents.tasksTable.TasksTableController;
import components.tasks.TasksAdminController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class TaskManagementController extends components.mainControllers.Controllers{
    private TasksAdminController mainController;
    private BooleanProperty canNext;
    private TasksTableController tasksTableController;
    private Parent tasksTableParent;
    private TaskManagementDetailsController taskManagementDetailsController;
    private Parent taskManagementDetailsParent;

    @FXML
    private StackPane main_screen;

    @FXML
    private StackPane table_SP;

    @FXML
    private Button next_btn;

    @FXML
    void ClickNext(ActionEvent event) {
        setDetailFxml();
        this.setDetailScreen();
    }

    public void setDetailScreen(){
        this.appController.setArea(getTaskManagementDetailsParent());
        if(this.tasksTableController.getTaskFXSelected() != null){
            this.taskManagementDetailsController.setScreenStaticDetails(this.tasksTableController.getTaskFXSelected().getGraphName() ,this.tasksTableController.getTaskFXSelected().getName());
            this.taskManagementDetailsController.setTables();
        }
    }

    public Parent getTaskManagementDetailsParent() {
        return taskManagementDetailsParent;
    }

    private void setDetailFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS_MANAGEMENT_DETAILS.toString());
            fxmlLoader.setLocation(url);
            this.taskManagementDetailsParent = fxmlLoader.load(url.openStream());
            this.taskManagementDetailsController= fxmlLoader.getController();
            this.taskManagementDetailsController.setAppController(this.appController);
            this.taskManagementDetailsController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        canNext = new SimpleBooleanProperty(false);
        this.next_btn.disableProperty().bind(this.canNext.not());
    }

    public void setMainController(TasksAdminController mainControllers) {
        this.mainController = mainControllers;
    }

    public BooleanProperty canNextProperty() {
        return canNext;
    }

    public TasksTableController getTaskManagementController() {
        return tasksTableController;
    }

    public void seTaskTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASKS_TABLE.toString());
            fxmlLoader.setLocation(url);
            tasksTableParent= fxmlLoader.load(url.openStream());
            this.tasksTableController = fxmlLoader.getController();
            this.tasksTableController.setAppController(this.appController);
            this.appController.setArea(this.table_SP ,tasksTableParent);
            this.tasksTableController.getTable().prefHeightProperty().bind(this.table_SP.heightProperty().multiply(0.925));
            this.tasksTableController.getTable().prefWidthProperty().bind(this.table_SP.widthProperty().multiply(0.925));
            this.tasksTableController.startListRefresher();
            this.canNext.bind(this.tasksTableController.isSelectedProperty());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public TasksAdminController getMainController() {
        return mainController;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
