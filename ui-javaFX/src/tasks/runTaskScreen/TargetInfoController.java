package tasks.runTaskScreen;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import dtoObjects.TargetRuntimeDTO;
import enums.StyleSheetsPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private StackPane serial_set_SP;

    @FXML
    private Button target_icon;

    @FXML
    private Label target_status_label;

    @FXML
    private ListView<String> serial_set_LIST;

    @FXML
    private VBox frozen_VB;

    @FXML
    private ListView<String> frozen_dependencies_LIST;

    @FXML
    private VBox waiting_VB;

    @FXML
    private Label ms_wait_in_line_label;

    @FXML
    private VBox skipped_VB;

    @FXML
    private ListView<String> skipped_failed_dependencies_LIST;

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
        this.isLight.addListener((a, b, c) -> {
            if (this.isLight.getValue()) {
                this.fall_screen_SP.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.fall_screen_SP.getStylesheets().remove(StyleSheetsPath.TASK_DARK.toString());
                this.fall_screen_SP.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.fall_screen_SP.getStylesheets().add(StyleSheetsPath.TASK_LIGHT.toString());
            } else {
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

    public void setPopUp(TargetRuntimeDTO targetRuntimeDTO){
        String colorIcon = "";
        this.target_name_label.setText(targetRuntimeDTO.getName());
        this.target_icon.setText(targetRuntimeDTO.getName());
        this.target_status_label.setText(targetRuntimeDTO.getPosition().toString());
        setSerialSet(targetRuntimeDTO);
        if(!targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.NONE)){
            if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.SKIPPED)){
                setSkippedTargetPopup(targetRuntimeDTO);
                colorIcon = "skipped";
            }
            else{
                setFinishTargetPopup(targetRuntimeDTO);
                colorIcon = targetRuntimeDTO.getFinishStatus().toString();
            }
        }
        else{
            if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.WAITING)){
                setWaitingTargetPopup(targetRuntimeDTO);
                colorIcon = "waiting";
            }
            else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.IN_PROCESS)){
                setProcessTargetPopup(targetRuntimeDTO);
                colorIcon = "process";
            }
            else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.SKIPPED)){
                setSkippedTargetPopup(targetRuntimeDTO);
                colorIcon = "skipped";
            }
            else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.FROZEN)){
                setFrozenTargetPopup(targetRuntimeDTO);
                colorIcon = "frozen";
            }
        }
        if(!colorIcon.equals(""))
            this.target_icon.getStyleClass().add(colorIcon);
    }

    private void setSerialSet(TargetRuntimeDTO targetRuntimeDTO){
        if(targetRuntimeDTO.getSerialSets() != null && targetRuntimeDTO.getSerialSets().size() == 0){
            this.serial_set_LIST.setVisible(false);
            Label label = new Label();
            label.setText("There is no serial set to this target");
            serial_set_SP.setAlignment(Pos.TOP_LEFT);
            this.serial_set_SP.getChildren().add(0,label);
        }
        else {
            for (String target : targetRuntimeDTO.getSerialSets()) {
                this.serial_set_LIST.getItems().add(target);
            }
        }

    }

    private void setFrozenTargetPopup(TargetRuntimeDTO targetRuntimeDTO){
        setAllPopupVBVisible();
        this.frozen_VB.setVisible(true);
        for(String target: targetRuntimeDTO.getDependsOn()) {
            this.frozen_dependencies_LIST.getItems().add(target);
        }
    }

    private void setAllPopupVBVisible(){
        this.frozen_VB.setVisible(false);
        this.waiting_VB.setVisible(false);
        this.in_process_VB.setVisible(false);
        this.skipped_VB.setVisible(false);
        this.finished_VB.setVisible(false);
    }

    private void setWaitingTargetPopup(TargetRuntimeDTO targetRuntimeDTO){
        setAllPopupVBVisible();
        this.waiting_VB.setVisible(true);
        this.ms_wait_in_line_label.setText(String.valueOf(targetRuntimeDTO.getJoinWaitingTimeStamp()));
    }

    private void setSkippedTargetPopup(TargetRuntimeDTO targetRuntimeDTO){
        setAllPopupVBVisible();
        this.skipped_VB.setVisible(true);
        for(String target: targetRuntimeDTO.getSkippedBecause()) {
            this.skipped_failed_dependencies_LIST.getItems().add(target);
        }
    }

    private void setProcessTargetPopup(TargetRuntimeDTO targetRuntimeDTO){
        setAllPopupVBVisible();
        this.in_process_VB.setVisible(true);
        this.ms_processing_label.setText(String.valueOf(targetRuntimeDTO.getProcessStartTimeStamp()));
    }

    private void setFinishTargetPopup(TargetRuntimeDTO targetRuntimeDTO){
        setAllPopupVBVisible();
        this.finished_VB.setVisible(true);
        this.finish_status_label.setText(targetRuntimeDTO.getFinishStatus().toString());
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TargetController mainControllers) {
        this.mainController = mainControllers;
    }
}
