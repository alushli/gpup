package components.menu;

import components.actions.ActionsController;
import components.appScreen.AppController;
import components.adminEnums.AppFxmlPath;
import components.dashboard.DashboardController;
import components.generalInfo.GeneralInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import components.loadFile.LoadFileController;
import components.menu.subMenu.SubMenuController;
import java.io.IOException;
import java.net.URL;

public class MenuController extends components.mainControllers.Controllers {
    private static LoadFileController loadFileComponentController = null;
    private static Parent loadFileParent;
    private static GeneralInfoController generalInfoComponentController = null;
    private static Parent generalInfoParent;
    private static ActionsController actionsComponentController = null;
    private static Parent actionsParent;
    private static Parent dashboardParent;
    private static SubMenuController subMenuComponentController = null;
    private static DashboardController dashboardController = null;
    private Parent subMenuParent;

    @FXML
    private GridPane main_screen;

    @FXML
    private CheckBox animation_cb;

    @FXML
    private Button dashboard_btn;

    public void setSubMenu(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.SUB_MENU.toString());
            fxmlLoader.setLocation(url);
            this.subMenuParent = fxmlLoader.load(url.openStream());
            this.subMenuComponentController= fxmlLoader.getController();
            this.subMenuComponentController.setAppController(this.appController);
            this.subMenuComponentController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        appController.setMenu(this.subMenuParent);
    }

    @FXML
    public void clickAction(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setActionButtons();
        if(actionsComponentController == null) {
            setActionsFxml();
        }
    }

    @FXML
    void clickDashboard(ActionEvent event) {
        if(dashboardController == null) {
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
            this.dashboardController= fxmlLoader.getController();
            this.dashboardController.setAppController(this.appController);
            this.dashboardController.setMainController(this);
            this.dashboardController.setTableScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setActionsFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.ACTIONS.toString());
            fxmlLoader.setLocation(url);
            this.actionsParent = fxmlLoader.load(url.openStream());
            this.actionsComponentController= fxmlLoader.getController();
            this.actionsComponentController.setAppController(this.appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickGeneralInfo(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setGeneralInfoButtons();
        if(generalInfoComponentController == null) {
            setGeneralInfoFxml();
        }
    }

    void setGeneralInfoFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.GENERAL_INFO.toString());
            fxmlLoader.setLocation(url);
            this.generalInfoParent = fxmlLoader.load(url.openStream());
            this.generalInfoComponentController= fxmlLoader.getController();
            this.generalInfoComponentController.setAppController(this.appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickLoadFile(ActionEvent event) {
        if(loadFileComponentController == null) {
            setLoadFileFxml();
        }
        this.loadFileComponentController.checkTaskRun();
        appController.setArea(this.loadFileParent);
    }

    void setLoadFileFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.LOAD_FILE.toString());
            fxmlLoader.setLocation(url);
            this.loadFileParent = fxmlLoader.load(url.openStream());
            this.loadFileComponentController= fxmlLoader.getController();
            this.loadFileComponentController.setAppController(this.appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickTasks(ActionEvent event) {
    }

    @FXML
    void clickAnimation(ActionEvent event) {
        this.appController.setIsAnimation(this.animation_cb.isSelected());
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public ActionsController getActionController() {return this.actionsComponentController;}

    public GeneralInfoController getGeneralInfoController() {
        return this.generalInfoComponentController;
    }

    public LoadFileController getLoadFileComponentController() {
        return this.loadFileComponentController;
    }

}
