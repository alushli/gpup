package menu;

import actions.ActionsController;
import appScreen.AppController;
import enums.FxmlPath;
import enums.ScreenTypes;
import generalInfo.GeneralInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import loadFile.LoadFileController;
import menu.subMenu.SubMenuController;
import tasks.TasksController;

import java.io.IOException;
import java.net.URL;

public class MenuController extends mainControllers.Controllers {
    private LoadFileController loadFileComponentController;
    private TasksController tasksComponentController;
    private GeneralInfoController generalInfoComponentController;
    private ActionsController actionsComponentController;
    private SubMenuController subMenuComponentController;

    @FXML
    private Button load_file_btn;

    @FXML
    private Button general_info_btn;

    @FXML
    private Button actions_btn;

    @FXML
    private Button tasks_btn;

    @FXML
    private CheckBox animation_cb;

    @FXML
    private ComboBox<String> skin_combo_box;

    @FXML
    public void initialize() {
        skin_combo_box.getItems().removeAll(skin_combo_box.getItems());
        skin_combo_box.getItems().addAll("Light", "Dark");
        skin_combo_box.getSelectionModel().select("Light");
    }

    public void setSubMenu(){
        Parent menu = getRoot(FxmlPath.SUB_MENU.toString(), ScreenTypes.SUB_MENU);
        appController.setMenu(menu);
    }

    @FXML
    public void clickAction(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setActionButtons();
        getRoot("../" + FxmlPath.ACTIONS, ScreenTypes.ACTIONS);
    }

    @FXML
    public void clickGeneralInfo(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setGeneralInfoButtons();
        getRoot("../" + FxmlPath.GENERAL_INFO, ScreenTypes.GENERAL_INFO);
    }

    @FXML
    public void clickLoadFile(ActionEvent event) {
        Parent data = getRoot("../" + FxmlPath.LOAD_FILE, ScreenTypes.LOAD_FILE);
        appController.setArea(data);
    }

    @FXML
    public void clickTasks(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setTasksButtons();
        getRoot("../" + FxmlPath.TASKS, ScreenTypes.TASKS);
    }

    Parent getRoot(String path, ScreenTypes screenName)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(path);
            fxmlLoader.setLocation(url);
            Parent data = fxmlLoader.load(url.openStream());
            switch (screenName){
                case LOAD_FILE:
                    this.loadFileComponentController = fxmlLoader.getController();
                    this.loadFileComponentController.setAppController(this.appController);
                    break;
                case GENERAL_INFO:
                    this.generalInfoComponentController = fxmlLoader.getController();
                    this.generalInfoComponentController.setAppController(this.appController);
                    break;
                case TASKS:
                    this.tasksComponentController = fxmlLoader.getController();
                    this.tasksComponentController.setAppController(this.appController);
                    break;
                case ACTIONS:
                    this.actionsComponentController = fxmlLoader.getController();
                    this.actionsComponentController.setAppController(this.appController);
                    break;
                case SUB_MENU:
                    this.subMenuComponentController = fxmlLoader.getController();
                    this.subMenuComponentController.setAppController(this.appController);
                    this.subMenuComponentController.setMainController(this);
                    break;
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @FXML
    void clickAnimation(ActionEvent event) {

    }

    @FXML
    void clickSkin(ActionEvent event) {

    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public ActionsController getActionController() {return this.actionsComponentController;}

    public TasksController getTasksController() {
        return this.tasksComponentController;
    }

    public GeneralInfoController getGeneralInfoController() {
        return this.generalInfoComponentController;
    }

    public LoadFileController getLoadFileComponentController() {
        return this.loadFileComponentController;
    }

}
