package tasks.runTaskScreen;

import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import enums.StyleSheetsPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TargetInfoController extends mainControllers.Controllers{
    private TargetController mainController;
    private BooleanProperty isLight;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label target_name_label;

    @FXML
    private Button target_icon;

    @FXML
    private Label target_status_label;

    @FXML
    private ListView<?> serial_set_LIST;

    @FXML
    private VBox frozen_VB;

    @FXML
    private ListView<?> frozen_dependencies_LIST;

    @FXML
    private VBox waiting_VB;

    @FXML
    private Label ms_wait_in_line_label;

    @FXML
    private VBox skipped_VB;

    @FXML
    private ListView<?> skipped_failed_dependencies_LIST2;

    @FXML
    private VBox in_process_VB;

    @FXML
    private Label ms_processing_label;

    @FXML
    private VBox finished_VB;

    @FXML
    private Label finish_status_label;

    @FXML
    public void initialize() {
        this.isLight = new SimpleBooleanProperty(true);
        this.isLight.addListener((a,b,c)->{
            if(this.isLight.getValue()){
                this.fall_screen_SP.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.fall_screen_SP.getStylesheets().remove(StyleSheetsPath.TASK_DARK.toString());
                this.fall_screen_SP.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.fall_screen_SP.getStylesheets().add(StyleSheetsPath.TASK_LIGHT.toString());
            }else{
                this.fall_screen_SP.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.fall_screen_SP.getStylesheets().remove(StyleSheetsPath.TASK_LIGHT.toString());
                this.fall_screen_SP.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.fall_screen_SP.getStylesheets().add(StyleSheetsPath.TASK_DARK.toString());
            }
        });
    }

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    public void setPopUp(TargetFXDTO targetFXDTO){
        this.target_name_label.setText(targetFXDTO.getName());
        this.target_icon.setText(targetFXDTO.getName());
        this.target_status_label.setText(targetFXDTO.getPosition());


    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TargetController mainControllers) {
        this.mainController = mainControllers;
    }
}
