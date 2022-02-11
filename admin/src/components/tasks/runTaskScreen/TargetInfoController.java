package components.tasks.runTaskScreen;

import components.adminEnums.TargetRunStatusAdmin;
import components.adminEnums.TargetRuntimeStatusAdmin;
import components.appScreen.AppController;
import components.dtoObjects.TargetRuntimeDTOFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TargetInfoController extends components.mainControllers.Controllers{
    private TargetController mainController;

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

    }

    public void setPopUp(TargetRuntimeDTOFX targetRuntimeDTO){
        String colorIcon = "";
        this.target_name_label.setText(targetRuntimeDTO.getName());
        this.target_icon.setText(targetRuntimeDTO.getName());
        this.target_status_label.setText(targetRuntimeDTO.getPosition().toString());
        if(!targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.NONE)){
            if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.SKIPPED)){
                setSkippedTargetPopup(targetRuntimeDTO);
                colorIcon = "skipped";
            }
            else{
                setFinishTargetPopup(targetRuntimeDTO);
                colorIcon = targetRuntimeDTO.getFinishStatus().toString();
            }
        }
        else{
            if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.WAITING)){
                setWaitingTargetPopup(targetRuntimeDTO);
                colorIcon = "waiting";
            }
            else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.IN_PROCESS)){
                setProcessTargetPopup(targetRuntimeDTO);
                colorIcon = "process";
            }
            else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.SKIPPED)){
                setSkippedTargetPopup(targetRuntimeDTO);
                colorIcon = "skipped";
            }
            else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.FROZEN)){
                setFrozenTargetPopup(targetRuntimeDTO);
                colorIcon = "frozen";
            }
        }
        if(!colorIcon.equals(""))
            this.target_icon.getStyleClass().add(colorIcon);
    }


    private void setFrozenTargetPopup(TargetRuntimeDTOFX targetRuntimeDTO){
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

    private void setWaitingTargetPopup(TargetRuntimeDTOFX targetRuntimeDTO){
        setAllPopupVBVisible();
        this.waiting_VB.setVisible(true);
        this.ms_wait_in_line_label.setText(String.valueOf(targetRuntimeDTO.getJoinWaitingTimeStamp()));
    }

    private void setSkippedTargetPopup(TargetRuntimeDTOFX targetRuntimeDTO){
        setAllPopupVBVisible();
        this.skipped_VB.setVisible(true);
        for(String target: targetRuntimeDTO.getSkippedBecause()) {
            this.skipped_failed_dependencies_LIST.getItems().add(target);
        }
    }

    private void setProcessTargetPopup(TargetRuntimeDTOFX targetRuntimeDTO){
        setAllPopupVBVisible();
        this.in_process_VB.setVisible(true);
        this.ms_processing_label.setText(String.valueOf(targetRuntimeDTO.getProcessStartTimeStamp()));
    }

    private void setFinishTargetPopup(TargetRuntimeDTOFX targetRuntimeDTO){
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
