package tasks.runTaskScreen;

import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class TargetController extends mainControllers.Controllers{
    private TargetFXDTO targetFXDTO;
    private BooleanProperty isLight;

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
        System.out.println(this.targetFXDTO.getName());
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
