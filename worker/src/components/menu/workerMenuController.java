package components.menu;

import components.appScreen.AppController;
import components.dashboard.DashboardController;
import components.workerEnums.AppFxmlPath;
import components.workerMainControllers.workerControllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class workerMenuController extends workerControllers {
    private static Parent dashboardParent;
    private static DashboardController dashboardController = null;

    @FXML
    private GridPane main_screen;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button task_managments_btn;


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void clickTaskManagments(ActionEvent event) {

    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
