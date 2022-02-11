package components.tasks.runTaskScreen;

import components.adminEnums.AppFxmlPath;
import components.appScreen.AppController;
import components.dtoObjects.TargetRuntimeDTOFX;
import components.tasks.CreateNewTasksController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TargetController extends components.mainControllers.Controllers{
    private CreateNewTasksController mainController;
    private TargetRuntimeDTOFX targetRuntimeDTO;
    private Stage popupWindow;

    @FXML
    private StackPane target_SP;

    @FXML
    private Button target_btn;

    public void setTargetRuntimeDTO(TargetRuntimeDTOFX targetRuntimeDTO) {
        this.targetRuntimeDTO = targetRuntimeDTO;
    }

    public Button getTarget_btn() {
        return target_btn;
    }

    @FXML
    public void initialize() {
    }

    @FXML
    void clickTarget(ActionEvent event) {
        setPopup();
    }

    private void setPopup(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(AppFxmlPath.TARGET_INFO_POPUP_TASK.toString()));
            Parent popup = (Parent) loader.load();
            TargetInfoController targetInfoController = loader.getController();
            targetInfoController.setAppController(this.appController);
            targetInfoController.setMainController(this);
            targetInfoController.setPopUp(this.targetRuntimeDTO);
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

    public CreateNewTasksController getMainController() {
        return mainController;
    }

    public void setMainController(CreateNewTasksController createNewTasksController){ this.mainController = createNewTasksController;}

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
