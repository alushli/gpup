package tasks.runTaskScreen;

import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import enums.FxmlPath;
import generalInfo.showGraphInfo.detailsGraphScreen.exportGraphScreen.ExportGraphScreenController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TargetController extends mainControllers.Controllers{
    private TargetFXDTO targetFXDTO;
    private BooleanProperty isLight;
    private Stage popupWindow;

    @FXML
    private StackPane target_SP;

    @FXML
    private Button target_btn;

    public void setTargetFXDTO(TargetFXDTO targetFXDTO) {
        this.targetFXDTO = targetFXDTO;
    }

    public Button getTarget_btn() {
        return target_btn;
    }

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    @FXML
    public void initialize() {
        this.isLight = new SimpleBooleanProperty(true);
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
            targetInfoController.setPopUp(this.targetFXDTO);
            targetInfoController.isLightProperty().bind(this.appController.isLightProperty());
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

        }

    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
