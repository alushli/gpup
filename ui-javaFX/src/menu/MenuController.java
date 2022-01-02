package menu;

import actions.ActionsController;
import appScreen.AppController;
import enums.FxmlPath;
import enums.ScreenTypes;
import enums.StyleSheetsPath;
import generalInfo.GeneralInfoController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import loadFile.LoadFileController;
import menu.subMenu.SubMenuController;
import tasks.TasksController;

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
    private BooleanProperty isLight;

    @FXML
    private GridPane main_screen;

    @FXML
    private CheckBox animation_cb;

    @FXML
    private ComboBox<String> skin_combo_box;

    @FXML
    public void initialize() {
        skin_combo_box.getItems().removeAll(skin_combo_box.getItems());
        skin_combo_box.getItems().addAll("Light", "Dark");
        skin_combo_box.getSelectionModel().select("Light");
        this.isLight = new SimpleBooleanProperty(true);
        this.isLight.addListener((a,b,c)->{
            if(this.isLight.getValue()){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MENU_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MENU_LIGHT.toString());
            }else{
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MENU_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MENU_DARK.toString());
            }
        });
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
            this.subMenuComponentController.isLightProperty().bind(this.appController.isLightProperty());
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
        //appController.setArea(this.actionsParent);
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

    public boolean isIsLight() {
        return isLight.get();
    }

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    @FXML
    public void clickGeneralInfo(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setGeneralInfoButtons();
        if(generalInfoComponentController == null) {
            setGeneralInfoFxml();
        }
        //appController.setArea(this.generalInfoParent);
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
            this.loadFileComponentController.isLightProperty().bind(this.appController.isLightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickTasks(ActionEvent event) {
        setSubMenu();
        this.subMenuComponentController.setTasksButtons();
        if(tasksComponentController == null) {
            setTasksFxml();
        }
        //appController.setArea(this.tasksParent);
    }

    void setTasksFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TASKS.toString());
            fxmlLoader.setLocation(url);
            this.tasksParent = fxmlLoader.load(url.openStream());
            this.tasksComponentController= fxmlLoader.getController();
            this.tasksComponentController.setAppController(this.appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickAnimation(ActionEvent event) {
        this.appController.setIsAnimation(this.animation_cb.isSelected());
    }

    @FXML
    void clickSkin(ActionEvent event) {
        if(this.skin_combo_box.getValue().equals("Light")){
            this.appController.setIsLight(true);
        }else{
            this.appController.setIsLight(false);
        }
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
