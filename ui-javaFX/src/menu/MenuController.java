package menu;

import actions.ActionsController;
import appScreen.AppController;
import enums.FxmlPath;
import enums.StyleSheetsPath;
import generalInfo.GeneralInfoController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import loadFile.LoadFileController;
import menu.subMenu.SubMenuController;
import tasks.TasksController;
import templates.LoadFileError;

import java.io.IOException;
import java.net.URL;

public class MenuController extends mainControllers.Controllers {
    private static LoadFileController loadFileComponentController = null;
    private static Parent loadFileParent;
    private static TasksController tasksComponentController = null;
    private static Parent tasksParent;
    private static GeneralInfoController generalInfoComponentController = null;
    private static Parent generalInfoParent;
    private static ActionsController actionsComponentController = null;
    private static Parent actionsParent;
    private static SubMenuController subMenuComponentController = null;
    private Parent subMenuParent;
    private StringProperty skin;

    @FXML
    private GridPane main_screen;

    @FXML
    private CheckBox animation_cb;

    @FXML
    private ComboBox<String> skin_combo_box;

    @FXML
    public void initialize() {
        skin_combo_box.getItems().removeAll(skin_combo_box.getItems());
        skin_combo_box.getItems().addAll("Light", "Dark", "Princess");
        skin_combo_box.getSelectionModel().select("Light");
        this.skin = new SimpleStringProperty("Light");
        skinListener(this.skin, this.main_screen);
    }

    public void setSubMenu(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.SUB_MENU.toString());
            fxmlLoader.setLocation(url);
            this.subMenuParent = fxmlLoader.load(url.openStream());
            this.subMenuComponentController= fxmlLoader.getController();
            this.subMenuComponentController.setAppController(this.appController);
            this.subMenuComponentController.setMainController(this);
            this.subMenuComponentController.skinListener();
            this.subMenuComponentController.skinProperty().bind(this.appController.skinProperty());
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

    void setActionsFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.ACTIONS.toString());
            fxmlLoader.setLocation(url);
            this.actionsParent = fxmlLoader.load(url.openStream());
            this.actionsComponentController= fxmlLoader.getController();
            this.actionsComponentController.setAppController(this.appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSkin() {
        return this.skin.getValue();
    }

    public StringProperty skinProperty() {
        return skin;
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
            URL url = getClass().getResource(FxmlPath.GENERAL_INFO.toString());
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
        appController.setArea(this.loadFileParent);
    }

    void setLoadFileFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.LOAD_FILE.toString());
            fxmlLoader.setLocation(url);
            this.loadFileParent = fxmlLoader.load(url.openStream());
            this.loadFileComponentController= fxmlLoader.getController();
            this.loadFileComponentController.setAppController(this.appController);
            this.loadFileComponentController.skinProperty().bind(this.appController.skinProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickTasks(ActionEvent event) {
        setTasksFxml();
        this.setLoadFileHandlingTask();
    }

    public void setLoadFileHandlingTask(){
        this.appController.setArea(this.tasksParent);
        if(!this.appController.getLoadFile()){
            tasksComponentController.getDetails_grid().setVisible(false);
            LoadFileError.setLoadFileError(tasksComponentController.getData_area(), this.appController);
        } else {
            LoadFileError.removeLoadFileError(tasksComponentController.getData_area());
            tasksComponentController.getDetails_grid().setVisible(true);
            tasksComponentController.setTableScreen();
            tasksComponentController.setPageScreen(true);
        }
    }

    void setTasksFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TASKS.toString());
            fxmlLoader.setLocation(url);
            this.tasksParent = fxmlLoader.load(url.openStream());
            this.tasksComponentController= fxmlLoader.getController();
            this.tasksComponentController.setAppController(this.appController);
            this.tasksComponentController.skinProperty().bind(this.appController.skinProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void skinListener(StringProperty skin, GridPane stackPane){
        skin.addListener((a, b, c)->{
            if(skin.getValue().equals("Light")){
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_PRINCESS.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MENU_DARK.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MENU_PRINCESS.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MENU_LIGHT.toString());
            }else if (skin.getValue().equals("Dark")){
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_PRINCESS.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MENU_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MENU_PRINCESS.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MENU_DARK.toString());
            } else {
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MENU_LIGHT.toString());
                stackPane.getStylesheets().remove(StyleSheetsPath.MENU_DARK.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MAIN_CSS_PRINCESS.toString());
                stackPane.getStylesheets().add(StyleSheetsPath.MENU_PRINCESS.toString());
            }
        });
    }

    @FXML
    void clickAnimation(ActionEvent event) {
        this.appController.setIsAnimation(this.animation_cb.isSelected());
    }

    @FXML
    void clickSkin(ActionEvent event) {
        this.appController.setSkin(this.skin_combo_box.getValue());
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
