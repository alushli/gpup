package tasks.runTaskScreen;

import appScreen.AppController;
import dtoObjects.TargetRuntimeDTO;
import enums.FxmlPath;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tasks.TasksController;

public class TargetController extends mainControllers.Controllers{
    private TasksController mainController;
    private TargetRuntimeDTO targetRuntimeDTO;
    private StringProperty skin;
    private Stage popupWindow;

    @FXML
    private StackPane target_SP;

    @FXML
    private Button target_btn;

    public void setTargetRuntimeDTO(TargetRuntimeDTO targetRuntimeDTO) {
        this.targetRuntimeDTO = targetRuntimeDTO;
    }

    public Button getTarget_btn() {
        return target_btn;
    }

    public StringProperty skinProperty() {
        return skin;
    }

    public void skinListener(){
        this.mainController.setLightListener(this.skin, this.target_SP);
    }

    @FXML
    public void initialize() {
        this.skin = new SimpleStringProperty("Light");
    }

    @FXML
    void clickTarget(ActionEvent event) {
        setPopup();
    }

    private void setPopup(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlPath.TARGET_INFO_POPUP_TASK.toString()));
            Parent popup = (Parent) loader.load();
            TargetInfoController targetInfoController = loader.getController();
            targetInfoController.setAppController(this.appController);
            targetInfoController.setMainController(this);
            targetInfoController.setPopUp(this.targetRuntimeDTO);
            targetInfoController.skinProperty().bind(this.appController.skinProperty());
            targetInfoController.skinListener();
            Scene secondScene = new Scene(popup, 400, 500);
            this.popupWindow = new Stage();
            this.popupWindow.setResizable(false);
            this.popupWindow.setTitle("Target Information");
            this.popupWindow.setAlwaysOnTop(true);
            this.popupWindow.setScene(secondScene);
            this.popupWindow.setX(this.appController.getPrimaryStage().getX() + 200);
            this.popupWindow.setY(this.appController.getPrimaryStage().getY() + 100);
            this.popupWindow.show();
        }catch (Exception e){
            System.out.println("Set popup error");
        }

    }

    public TasksController getMainController() {
        return mainController;
    }

    public void setMainController(TasksController tasksController){ this.mainController = tasksController;}

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
