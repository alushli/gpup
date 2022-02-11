package components.tasks.runTaskScreen;

import components.adminEnums.AppFxmlPath;
import components.adminEnums.SimulationEntryPointAdmin;
import components.appScreen.AppController;
import components.dtoObjects.TargetRuntimeDTOFX;
import components.tasks.CreateNewTasksController;
import components.tasks.UIAdapter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public class RunTaskController extends components.mainControllers.Controllers{
    private CreateNewTasksController mainController;
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
    private SimulationEntryPointAdmin entryPoint;
    private int maxParallel;
    private String sourceFolderCompiler;
    private String productFolderCompiler;

    @FXML
    private TextArea log_TA;

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
    private Button new_task_btn;

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
    private Button pause_btn;

    @FXML
    private Button resume_btn;

    @FXML
    private Button start_btn;

    @FXML
    private VBox threads_VB;

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @FXML
    public void initialize() {
        this.new_task_btn.setDisable(true);
        this.threads_VB.setVisible(false);
        this.skippedTargets = new SimpleStringProperty();
        this.failedTargets = new SimpleStringProperty();
        this.warningTargets = new SimpleStringProperty();
        this.successTargets = new SimpleStringProperty();
        this.count_skipped.textProperty().bind(this.skippedTargets);
        this.count_failed.textProperty().bind(this.failedTargets);
        this.count_warning.textProperty().bind(this.warningTargets);
        this.count_success.textProperty().bind(this.successTargets);
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    public Label getTask_name_label() {
        return task_name_label;
    }

    private void createTargetBox(TargetRuntimeDTOFX targetFXDTO, FlowPane flowPane, String colorStatus) {
      try {
          FXMLLoader fxmlLoader = new FXMLLoader();
          URL url = getClass().getResource(AppFxmlPath.TARGET_BOX_TASK.toString());
          fxmlLoader.setLocation(url);
          Node targetBox = fxmlLoader.load();
          TargetController targetController = fxmlLoader.getController();
          targetController.setAppController(this.appController);
          targetController.setMainController(this.mainController);
          targetController.setTargetRuntimeDTO(targetFXDTO);
          targetController.getTarget_btn().setText(targetFXDTO.getName());
          if (targetBox instanceof StackPane) {
              StackPane stackPane = (StackPane) targetBox;
              if (stackPane.getChildren().get(0) instanceof Button) {
                  Button button = (Button) stackPane.getChildren().get(0);
                  button.getStyleClass().add(colorStatus);
              }
          }
          flowPane.getChildren().add(targetBox);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    @FXML
    void clickPause(ActionEvent event) {
        this.pause_btn.setDisable(true);
        this.resume_btn.setDisable(false);
        this.threads_VB.setVisible(true);
       // if(this.task_name_label.getText().equals("Simulation Task"))
            //this.appController.getEngineManager().setPaused(true, "Simulation Task");
       // else
           // this.appController.getEngineManager().setPaused(true, "Compiler Task");
    }

    @FXML
    void clickResume(ActionEvent event) {
        this.resume_btn.setDisable(true);
        this.pause_btn.setDisable(false);
        this.threads_VB.setVisible(false);
       // if(this.task_name_label.getText().equals("Simulation Task")) {
            //this.appController.getEngineManager().setPaused(false, "Simulation Task");
            //this.appController.getEngineManager().changeThreadsNum(Integer.parseInt(this.threads_num_label.getText()), TasksName.SIMULATION);
        //}
        //else {
            //this.appController.getEngineManager().setPaused(false, "Compiler Task");
            //this.appController.getEngineManager().changeThreadsNum(Integer.parseInt(this.threads_num_label.getText()), TasksName.COMPILATION);
        //}

    }

    @FXML
    void clickStart(ActionEvent event) {
        this.start_btn.setDisable(true);
        this.pause_btn.setDisable(false);
        if(taskType.equals("Simulation Task")) {
            Consumer<String> consumer = s-> updateLog(s);
            UIAdapter uiAdapter = createUIAdapter();
           // SimulationTask task = new SimulationTask(this.appController.getEngineManager(), uiAdapter, this.targetsToRun, this.processTimeSimulation, this.chanceTargetSuccessSimulation, this.chanceTargetWarningSimulation, this.isRandomSimulation, this.entryPoint, consumer, this.maxParallel);
           // new Thread(task).start();
        } else{
            Consumer<String> consumer = s-> updateLog(s);
            UIAdapter uiAdapter = createUIAdapter();
           // CompilerTask task = new CompilerTask(this.appController.getEngineManager(), uiAdapter,this.targetsToRun, this.sourceFolderCompiler, this.productFolderCompiler , this.entryPoint, consumer, this.maxParallel);
           // new Thread(task).start();
        }
    }

    @FXML
    void clickNewTask(ActionEvent event) {
        //this.appController.getEngineManager().setSimulationTaskManager(null);
        //this.appController.getEngineManager().setCompilerTaskManager(null);
        //this.appController.getMenuComponentController().clickTasks(event);
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
                if(Integer.parseInt(this.all_targets_to_run_count.getText()) == Integer.parseInt(this.finish_targets_count.getText())) {
                    this.new_task_btn.setDisable(false);
                    this.pause_btn.setDisable(true);
                    this.resume_btn.setDisable(true);
                }

        }, size -> {
                this.all_targets_to_run_count.setText(size);
            }, progress -> {
            this.progress_bar.setProgress(progress);
        });
        return uiAdapter;
    }

    private void updateLog(String str){
        Platform.runLater(
                () -> {
                    this.log_TA.appendText(str + "\n");
                }
        );
    }

  private void addToFPWhatDontExist(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection, FlowPane flowPane, String colorStatus){
        for(TargetRuntimeDTOFX targetRuntimeDTO: targetRuntimeDTOCollection){
            Node node = getSelectedNodeOfFP(targetRuntimeDTO.getName(), flowPane);
            if(node == null)
                createTargetBox(targetRuntimeDTO, flowPane, colorStatus);
        }
  }

    private void removeFromFPWhatExist(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection, FlowPane flowPane) {
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

    private boolean isOnFlowPaneChildren(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection, String name){
      for(TargetRuntimeDTOFX targetRuntimeDTO: targetRuntimeDTOCollection){
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

    public void addToWaiting(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.waiting_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.waiting_FP, "waiting");
    }
    public void addToProcess(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.in_progress_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.in_progress_FP, "process");
    }
    public void addToSkipped(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.skipped_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.skipped_FP, "skipped");
        this.skippedTargets.setValue(String.valueOf(this.skipped_FP.getChildren().size()));
    }
    public void addToFailed(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.failed_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.failed_FP, "failure");
        this.failedTargets.setValue(String.valueOf(this.failed_FP.getChildren().size()));
    }
    public void addToSuccess(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.success_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.success_FP, "success");
        this.successTargets.setValue(String.valueOf(this.success_FP.getChildren().size()));
    }
    public void addToWarning(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.warning_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.warning_FP, "warning");
        this.warningTargets.setValue(String.valueOf(this.warning_FP.getChildren().size()));
    }


    public void addToFrozen(Set<TargetRuntimeDTOFX> targetRuntimeDTOCollection){
        removeFromFPWhatExist(targetRuntimeDTOCollection, this.frozen_FP);
        addToFPWhatDontExist(targetRuntimeDTOCollection, this.frozen_FP, "frozen");

    }

    public void setSimulationProperties(Collection<String> targetsToRun, int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPointAdmin entryPoint){
        this.targetsToRun = targetsToRun;
        this.processTimeSimulation = processTime;
        this.chanceTargetSuccessSimulation = chanceTargetSuccess;
        this.chanceTargetWarningSimulation = chanceTargetWarning;
        this.isRandomSimulation = isRandom;
        this.entryPoint = entryPoint;
    }

    public void setCompilerProperties(Collection<String> targetsToRun, String sourceFolder, String productFolder, SimulationEntryPointAdmin entryPoint){
        this.targetsToRun = targetsToRun;
        this.sourceFolderCompiler = sourceFolder;
        this.productFolderCompiler = productFolder;
        this.entryPoint = entryPoint;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(CreateNewTasksController mainControllers) {
        this.mainController = mainControllers;
    }

}
