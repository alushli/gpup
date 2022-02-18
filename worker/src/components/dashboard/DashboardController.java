package components.dashboard;

import components.generalComponents.taskTable.WorkerTasksTableController;
import components.menu.WorkerMenuController;
import components.workerEnums.AppFxmlPath;
import components.appScreen.AppController;
import components.generalComponents.usersTable.UserTableController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;

public class DashboardController extends components.workerMainControllers.workerControllers{
    private WorkerMenuController mainController;
    private UserTableController userTableController;
    private Parent userTableParent;
    private WorkerTasksTableController workerTasksTableController;
    private Parent workerTasksTableParent;

    @FXML
    private Label dashboardTitle;

    @FXML
    private StackPane tasksContainer;

    @FXML
    private StackPane userTable;


    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(WorkerMenuController mainController) {
        this.mainController = mainController;
    }

    public void setUsersTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.DASHBOARD_USERS_TABLE.toString());
            fxmlLoader.setLocation(url);
            userTableParent= fxmlLoader.load(url.openStream());
            this.userTableController = fxmlLoader.getController();
            this.userTableController.setAppController(this.appController);
            this.appController.setArea(this.userTable ,userTableParent);
            this.userTableController.getTable().prefHeightProperty().bind(this.userTable.heightProperty().multiply(0.925));
            this.userTableController.startListRefresher();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTaskTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.DASHBOARD_TASK_TABLE.toString());
            fxmlLoader.setLocation(url);
            workerTasksTableParent= fxmlLoader.load(url.openStream());
            this.workerTasksTableController = fxmlLoader.getController();
            this.workerTasksTableController.setAppController(this.appController);
            this.appController.setArea(this.tasksContainer ,workerTasksTableParent);
            this.workerTasksTableController.getTable().prefHeightProperty().bind(this.tasksContainer.heightProperty().multiply(0.925));
            this.workerTasksTableController.startListRefresher();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public WorkerTasksTableController getWorkerTasksTableController() {
        return workerTasksTableController;
    }
}
