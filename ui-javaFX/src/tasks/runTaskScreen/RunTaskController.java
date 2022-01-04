package tasks.runTaskScreen;

import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import enums.FxmlPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tasks.TasksController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RunTaskController extends mainControllers.Controllers{
    private TasksController mainController;
    private BooleanProperty isLight;
    private ArrayList<TargetFXDTO> targetsArray;
    private StringProperty skippedTargets;
    private StringProperty failedTargets;
    private StringProperty warningTargets;
    private StringProperty successTargets;
    private int count = 0;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label task_name_label;

    @FXML
    private VBox skipped_VB1;

    @FXML
    private FlowPane frozen_FP;

    @FXML
    private FlowPane waiting_FP;

    @FXML
    private FlowPane in_progress_FP;

    @FXML
    private ProgressBar progress_bar;

    @FXML
    private Label finish_targets_count;

    @FXML
    private Label all_targets_to_run_count;

    @FXML
    private VBox skipped_VB;

    @FXML
    private FlowPane skipped_FP;

    @FXML
    private Label count_skipped;

    @FXML
    private VBox failed_VB;

    @FXML
    private FlowPane failed_FP;

    @FXML
    private Label count_failed;

    @FXML
    private VBox warning_VB;

    @FXML
    private FlowPane warning_FP;

    @FXML
    private Label count_warning;

    @FXML
    private VBox success_CV;

    @FXML
    private FlowPane success_FP;

    @FXML
    private Label count_success;

    @FXML
    public void initialize() {
        this.isLight = new SimpleBooleanProperty(true);
        this.skippedTargets = new SimpleStringProperty();
        this.failedTargets = new SimpleStringProperty();
        this.warningTargets = new SimpleStringProperty();
        this.successTargets = new SimpleStringProperty();
        this.count_skipped.textProperty().bind(this.skippedTargets);
        this.count_failed.textProperty().bind(this.failedTargets);
        this.count_warning.textProperty().bind(this.warningTargets);
        this.count_success.textProperty().bind(this.successTargets);
    }

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    public Label getTask_name_label() {
        return task_name_label;
    }

    public void setFrozenTargets(ArrayList<TargetFXDTO> arrayList){
        this.targetsArray = arrayList;
        for(int i = 0;i < arrayList.size();i++){
            createTargetBox(arrayList.get(i), this.frozen_FP);
        }
    }

    private void createTargetBox(TargetFXDTO targetFXDTO, FlowPane flowPane) {
      try {
          FXMLLoader fxmlLoader = new FXMLLoader();
          URL url = getClass().getResource(FxmlPath.TARGET_BOX_TASK.toString());
          fxmlLoader.setLocation(url);
          Node targetBox = fxmlLoader.load();
          TargetController targetController = fxmlLoader.getController();
          targetController.setAppController(this.appController);
          targetController.setTargetFXDTO(targetFXDTO);
          targetController.getTarget_btn().setText(targetFXDTO.getName());
          targetController.isLightProperty().bind(this.appController.isLightProperty());
          this.mainController.setLightListener(targetController.isLightProperty());
          flowPane.getChildren().add(targetBox);
      }catch (Exception e){}

    }

    private void moveTargetFrozenToSkipped(String name){
        Node node = getSelectedNodeOfFP(name, this.frozen_FP);
        if(node != null){
            this.frozen_FP.getChildren().remove(node);
            this.skipped_FP.getChildren().add(0,node);
            this.skippedTargets.setValue(String.valueOf(this.skipped_FP.getChildren().size()));
        }
    }

    private void moveTargetFrozenToSWaiting(String name){
        Node node = getSelectedNodeOfFP(name, this.frozen_FP);
        if(node != null){
            this.frozen_FP.getChildren().remove(node);
            this.waiting_FP.getChildren().add(0,node);
        }
    }

    private void moveTargetWaitingToProcess(String name){
        Node node = getSelectedNodeOfFP(name, this.waiting_FP);
        if(node != null){
            this.waiting_FP.getChildren().remove(node);
            this.in_progress_FP.getChildren().add(0,node);
        }
    }

    private void moveTargetProcessToFailed(String name){
        Node node = getSelectedNodeOfFP(name, this.in_progress_FP);
        if(node != null){
            this.in_progress_FP.getChildren().remove(node);
            this.failed_FP.getChildren().add(0,node);
            this.failedTargets.setValue(String.valueOf(this.failed_FP.getChildren().size()));
        }
    }

    private void moveTargetProcessToWarning(String name){
        Node node = getSelectedNodeOfFP(name, this.in_progress_FP);
        if(node != null){
            this.in_progress_FP.getChildren().remove(node);
            this.warning_FP.getChildren().add(0,node);
            this.warningTargets.setValue(String.valueOf(this.warning_FP.getChildren().size()));
        }
    }

    private void moveTargetProcessToSuccess(String name){
        Node node = getSelectedNodeOfFP(name, this.in_progress_FP);
        if(node != null){
            this.in_progress_FP.getChildren().remove(node);
            this.success_FP.getChildren().add(0,node);
            this.successTargets.setValue(String.valueOf(this.success_FP.getChildren().size()));
        }
    }

    private Node getSelectedNodeOfFP(String name, FlowPane flowPane){
        Node node = null;
        StackPane stackPane;
        Button button;
        boolean found = false;
        for(int i = 0;i < flowPane.getChildren().size() && !found;i++){
            node = flowPane.getChildren().get(i);
            if (node instanceof StackPane) {
                stackPane = (StackPane) node;
                if (stackPane.getChildren().get(0) instanceof Button) {
                    button = (Button) stackPane.getChildren().get(0);
                    if(button.getText().equals(name))
                       found = true;
                }
            }
        }
        if(found)
            return node;
        return null;
    }

    @FXML
    void clickCancel(ActionEvent event) {

    }

    @FXML
    void clickPause(ActionEvent event) {
    }

    @FXML
    void clickResume(ActionEvent event) {

    }

    @FXML
    void clickStart(ActionEvent event) {
        if(count ==1)
            moveTargetFrozenToSkipped("A");
        else if(count ==2){
            moveTargetFrozenToSWaiting("B");
            moveTargetFrozenToSWaiting("C");
            moveTargetFrozenToSWaiting("D");
            moveTargetFrozenToSWaiting("E");
            moveTargetFrozenToSWaiting("F");}
        else if(count ==3){
            moveTargetWaitingToProcess("B");
            moveTargetWaitingToProcess("C");
            moveTargetWaitingToProcess("D");
            moveTargetWaitingToProcess("F");}
        else if(count ==4)
            moveTargetProcessToFailed("B");
        else if(count ==5)
            moveTargetProcessToWarning("C");
        else if(count ==6){
            moveTargetProcessToSuccess("D");
            moveTargetProcessToSuccess("F");}
        else {}
        count++;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TasksController mainControllers) {
        this.mainController = mainControllers;
    }

}
