package components.menu;

import components.appScreen.AppController;
import components.dashboard.DashboardController;
import components.task.taskManagment.WorkerTaskManagementController;
import components.workerEnums.AppFxmlPath;
import components.workerMainControllers.workerControllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class WorkerMenuController extends workerControllers {
    private static Parent dashboardParent;
    private static DashboardController dashboardController = null;
    private static Parent workerTaskManagement;
    private static WorkerTaskManagementController workerTaskManagementController = null;

    @FXML
    private GridPane main_screen;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button task_managments_btn;

    @FXML
    private Label name_label;

    @FXML
        public void clickDashboard(ActionEvent event) {
        if (dashboardController == null) {
            setDashboardFxml();
        }
        appController.setArea(this.dashboardParent);
    }

    void setDashboardFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.DASHBOARD_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.dashboardParent = fxmlLoader.load(url.openStream());
            this.dashboardController = fxmlLoader.getController();
            this.dashboardController.setAppController(this.appController);
            this.dashboardController.setMainController(this);
            this.dashboardController.setUsersTableScreen();
            this.dashboardController.setTaskTableScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Label getName_label() {
        return name_label;
    }

    @FXML
    void clickTaskManagments(ActionEvent event) {
        setTaskManagementFxml();
        appController.setArea(this.workerTaskManagement);
    }

    void setTaskManagementFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASK_MANAGEMENT.toString());
            fxmlLoader.setLocation(url);
            this.workerTaskManagement = fxmlLoader.load(url.openStream());
            this.workerTaskManagementController = fxmlLoader.getController();
            this.workerTaskManagementController.setAppController(this.appController);
            this.workerTaskManagementController.setMainController(this);
            this.workerTaskManagementController.setTables();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
