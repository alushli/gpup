package tasks.runTaskScreen;

import Enums.SimulationEntryPoint;
import appScreen.AppController;
import dtoObjects.TargetFXDTO;
import dtoObjects.TargetRuntimeDTO;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tasks.TasksController;
import tasks.UIAdapter;
import tasks.simulation.SimulationTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public class RunTaskController extends mainControllers.Controllers{
    private TasksController mainController;
    private BooleanProperty isLight;
    private Collection<String> targetsToRun;
    private StringProperty skippedTargets;
    private StringProperty failedTargets;
    private StringProperty warningTargets;
    private StringProperty successTargets;
    private String taskType;
    private int processTimeSimulation;
    private double chanceTargetSuccessSimulation;
    private double chanceTargetWarningSimulation;
    private boolean isRandomSimulation;
    private SimulationEntryPoint entryPoint;
    private int maxParallel;

    private int count = 0;

    @FXML
    private ScrollPane logs_SP;

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
    private Button cancel_btn;

    @FXML
    private Button pause_btn;

    @FXML
    private Button resume_btn;

    @FXML
    private Button start_brn;

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

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

    private void createTargetBox(TargetRuntimeDTO targetFXDTO, FlowPane flowPane, String colorStatus) {
      try {
          FXMLLoader fxmlLoader = new FXMLLoader();
          URL url = getClass().getResource(FxmlPath.TARGET_BOX_TASK.toString());
          fxmlLoader.setLocation(url);
          Node targetBox = fxmlLoader.load();
          TargetController targetController = fxmlLoader.getController();
          targetController.setAppController(this.appController);
          targetController.setTargetRuntimeDTO(targetFXDTO);
          targetController.getTarget_btn().setText(targetFXDTO.getName());
          targetController.isLightProperty().bind(this.appController.isLightProperty());
          this.mainController.setLightListener(targetController.isLightProperty());
          if (targetBox instanceof StackPane) {
              StackPane stackPane = (StackPane) targetBox;
              if (stackPane.getChildren().get(0) instanceof Button) {
                  Button button = (Button) stackPane.getChildren().get(0);
                  button.getStyleClass().add(colorStatus);
              }
          }
          flowPane.getChildren().add(targetBox);
      }catch (Exception e){}

    }

    @FXML
    void clickCancel(ActionEvent event) {
        this.appController.getMenuComponentController().clickTasks(event);
    }

    @FXML
    void clickPause(ActionEvent event) {
        this.pause_btn.setDisable(true);
        this.resume_btn.setDisable(false);
    }

    @FXML
    void clickResume(ActionEvent event) {
        this.resume_btn.setDisable(true);
        this.pause_btn.setDisable(false);
    }

    @FXML
    void clickStart(ActionEvent event) {
        this.cancel_btn.setDisable(false);
        this.start_brn.setDisable(true);
        if(taskType.equals("Simulation Task")) {
            Consumer<String> consumer = s-> System.out.println(s);
            UIAdapter uiAdapter = createUIAdapter();
            SimulationTask task = new SimulationTask(this.appController.getEngineManager(), uiAdapter, this.targetsToRun, this.processTimeSimulation, this.chanceTargetSuccessSimulation, this.chanceTargetWarningSimulation, this.isRandomSimulation, this.entryPoint, consumer, this.maxParallel);
            new Thread(task).start();
        }
    }

    private UIAdapter createUIAdapter() {
        UIAdapter uiAdapter = new UIAdapter(
            list -> {
                addToFrozen(list);
            }, list -> {
                    addToWaiting(list);
            },list -> {
                    addToProcess(list);
            },list -> {
                    addToSkipped(list);
            },list -> {
                    addToFailed(list);
            },list -> {
                    addToWarning(list);
            },list -> {
                    addToSuccess(list);
            }, size ->{
                this.finish_targets_count.setText(size);
            }, size -> {
                this.all_targets_to_run_count.setText(size);
            }, progress -> {
                this.progress_bar.setProgress(progress);
        });
        return uiAdapter;
    }


  private void addToFPWhatDontExist(Set<TargetRuntimeDTO> targetRuntimeDTOCollection, FlowPane flowPane, String colorStatus){
        for(TargetRuntimeDTO targetRuntimeDTO: targetRuntimeDTOCollection){
            Node node = getSelectedNodeOfFP(targetRuntimeDTO.getName(), flowPane);
            if(node == null)
                createTargetBox(targetRuntimeDTO, flowPane, colorStatus);
        }
  }

    private void removeFromFPWhatExist(Set<TargetRuntimeDTO> targetRuntimeDTOCollection, FlowPane flowPane) {
        Node node;
        StackPane stackPane;
        Button button;
        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            node = flowPane.getChildren().get(i);
            if (node instanceof StackPane) {
                stackPane = (StackPane) node;
                if (stackPane.getChildren().get(0) instanceof Button) {
                    button = (Button) stackPane.getChildren().get(0);
                    if(!isOnFlowPaneChildren(targetRuntimeDTOCollection, button.getText()))
                        flowPane.getChildren().remove(node);
                }
            }
        }
    }

    private boolean isOnFlowPaneChildren(Set<TargetRuntimeDTO> targetRuntimeDTOCollection, String name){
      for(TargetRuntimeDTO targetRuntimeDTO: targetRuntimeDTOCollection){
          if(targetRuntimeDTO.getName().equals(name))
              return true;
      }
      return false;
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

    public void addToWaiting(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.waiting_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.waiting_FP, "waiting");
    }
    public void addToProcess(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.in_progress_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.in_progress_FP, "process");
    }
    public void addToSkipped(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.skipped_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.skipped_FP, "skipped");
        this.skippedTargets.setValue(String.valueOf(this.skipped_FP.getChildren().size()));
    }
    public void addToFailed(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.failed_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.failed_FP, "failure");
        this.failedTargets.setValue(String.valueOf(this.failed_FP.getChildren().size()));
    }
    public void addToSuccess(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.success_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.success_FP, "success");
        this.successTargets.setValue(String.valueOf(this.success_FP.getChildren().size()));
    }
    public void addToWarning(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.warning_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.warning_FP, "warning");
        this.warningTargets.setValue(String.valueOf(this.warning_FP.getChildren().size()));
    }

    public void addToFrozen(Set<TargetRuntimeDTO> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.frozen_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.frozen_FP, "frozen");
    }

    public void setSimulationProperties(Collection<String> targetsToRun, int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPoint entryPoint, int maxParallel){
        this.targetsToRun = targetsToRun;
        this.processTimeSimulation = processTime;
        this.chanceTargetSuccessSimulation = chanceTargetSuccess;
        this.chanceTargetWarningSimulation = chanceTargetWarning;
        this.isRandomSimulation = isRandom;
        this.entryPoint = entryPoint;
        this.maxParallel = maxParallel;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TasksController mainControllers) {
        this.mainController = mainControllers;
    }

}
