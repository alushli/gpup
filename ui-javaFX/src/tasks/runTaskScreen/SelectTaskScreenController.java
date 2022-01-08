package tasks.runTaskScreen;

import Enums.SimulationEntryPoint;
import appScreen.AppController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import tasks.TasksController;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class SelectTaskScreenController extends mainControllers.Controllers {
    private TasksController mainController;
    private BooleanProperty isSimulation;
    private BooleanProperty processTimeValid;
    private BooleanProperty endSuccessValid;
    private BooleanProperty endWarningValid;
    private BooleanProperty srcFolderValid;
    private BooleanProperty compiledFolderValid;
    private BooleanProperty isLight;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private ComboBox<String> task_CB;

    @FXML
    private StackPane simulation_SP;

    @FXML
    private VBox simulation_content;

    @FXML
    private Label error_label;

    @FXML
    private TextField processing_time_simulation;

    @FXML
    private ComboBox<String> random_simulation;

    @FXML
    private TextField end_success_simulation;

    @FXML
    private TextField end_warnings_after_success_simulation;

    @FXML
    private VBox compiler_content;

    @FXML
    private Label source_compiler_label;

    @FXML
    private Label compiled_compiler_label;

    @FXML
    private CheckBox incremental_CB;

    @FXML
    private Label incremental_message_label;

    @FXML
    private ComboBox<Integer> thread_amount_CB;

    @FXML
    private Button next_btn;

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    @FXML
    void clickBack(ActionEvent event) {
        this.mainController.setSelectedTargets(this.mainController.getTargetsTableController().getCurSelected());
        this.mainController.getTargetsTableController().setSelectActive();
        this.mainController.setPageScreen(false);
        this.mainController.setTableButtonEnable();
        this.mainController.setIsOneTargetSelectFromTable(true);
    }


    @FXML
    void clickLoadCompiledCompiler(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            return;
        }
        String absolutePath = selectedDirectory.getAbsolutePath();
        this.compiled_compiler_label.setText(absolutePath);
        this.compiledFolderValid.set(true);
    }

    @FXML
    void clickLoadSourceCompiler(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            return;
        }
        String absolutePath = selectedDirectory.getAbsolutePath();
        this.source_compiler_label.setText(absolutePath);
        this.srcFolderValid.set(true);
    }

    @FXML
    void clickNext(ActionEvent event) {
        this.mainController.setRunTaskScreen();
        this.mainController.updateTaskName(this.task_CB.getValue());
        if(this.task_CB.getValue().equals("Simulation Task")){
            boolean isRandom = this.random_simulation.getValue().equals("Yes") ? true : false;
            if(this.incremental_CB.isSelected())
                this.mainController.updateSimulationTaskProperties(Integer.parseInt(this.processing_time_simulation.getText()), Double.parseDouble(this.end_success_simulation.getText()), Double.parseDouble(this.end_warnings_after_success_simulation.getText()), isRandom, SimulationEntryPoint.INCREMENTAL, this.thread_amount_CB.getValue());
            else
                this.mainController.updateSimulationTaskProperties(Integer.parseInt(this.processing_time_simulation.getText()), Double.parseDouble(this.end_success_simulation.getText()), Double.parseDouble(this.end_warnings_after_success_simulation.getText()),isRandom, SimulationEntryPoint.FROM_SCRATCH, this.thread_amount_CB.getValue());

        }
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @FXML
    public void initialize() {
        this.isLight = new SimpleBooleanProperty(true);
        this.isSimulation = new SimpleBooleanProperty(true);
        this.processTimeValid = new SimpleBooleanProperty(false);
        this.endSuccessValid = new SimpleBooleanProperty(false);
        this.endWarningValid = new SimpleBooleanProperty(false);
        this.srcFolderValid = new SimpleBooleanProperty(false);
        this.compiledFolderValid = new SimpleBooleanProperty(false);
        setPreInformationOnScreen();
        this.isSimulation.addListener((a,b,c)->{
            if(this.isSimulation.getValue()){
               this.compiler_content.setVisible(false);
               this.simulation_content.setVisible(true);
            }else{
                this.simulation_content.setVisible(false);
                this.compiler_content.setVisible(true);
            }
        });
        if(this.task_CB.getValue().equals("Simulation Task"))
            this.next_btn.disableProperty().bind((this.processTimeValid.and(this.endSuccessValid.and(this.endWarningValid)).not()));
        addListeners();
        this.task_CB.selectionModelProperty().addListener((a,b,c)->{
            if(this.task_CB.getValue().equals("Simulation Task")){
                this.compiler_content.setVisible(false);
                this.simulation_content.setVisible(true);
            } else{
                this.simulation_content.setVisible(false);
                this.compiler_content.setVisible(true);
            }
        });
    }

    private void addListeners() {
        this.processing_time_simulation.textProperty().addListener((a, b, c) -> {
            try {
                if (!this.processing_time_simulation.getText().isEmpty()) {
                    int processTime = Integer.parseInt(this.processing_time_simulation.getText());
                    if (processTime <= 0)
                        setInvalidArguments(this.processing_time_simulation, true, this.processTimeValid);
                    else
                        setInvalidArguments(this.processing_time_simulation, false, this.processTimeValid);
                } else
                    setInvalidArguments(this.processing_time_simulation, true, this.processTimeValid);
            } catch (NumberFormatException e) {
                setInvalidArguments(this.processing_time_simulation, true, this.processTimeValid);
            }
        });
        this.end_success_simulation.textProperty().addListener((a, b, c) -> {
            try {
                if (!this.end_success_simulation.getText().isEmpty()) {
                    double endSuccess = Double.parseDouble(this.end_success_simulation.getText());
                    if (endSuccess < 0 || endSuccess > 1)
                        setInvalidArguments(this.end_success_simulation, true, this.endSuccessValid);
                    else
                        setInvalidArguments(this.end_success_simulation, false, this.endSuccessValid);
                } else
                    setInvalidArguments(this.end_success_simulation, true, this.endSuccessValid);
            } catch (NumberFormatException e) {
                setInvalidArguments(this.end_success_simulation, true, this.endSuccessValid);
            }
        });
        this.end_warnings_after_success_simulation.textProperty().addListener((a, b, c) -> {
            try {
                if (!this.end_warnings_after_success_simulation.getText().isEmpty()) {
                    double endWarnings = Double.parseDouble(this.end_warnings_after_success_simulation.getText());
                    if (endWarnings < 0 || endWarnings > 1)
                        setInvalidArguments(this.end_warnings_after_success_simulation, true, this.endWarningValid);
                     else
                        setInvalidArguments(this.end_warnings_after_success_simulation, false, this.endWarningValid);
                } else
                    setInvalidArguments(this.end_warnings_after_success_simulation, true, this.endWarningValid);
            } catch (NumberFormatException e) {
                setInvalidArguments(this.end_warnings_after_success_simulation, true, this.endWarningValid);
            }
        });
    }

    private void setPreInformationOnScreen(){
        this.processing_time_simulation.setPromptText("Positive integer");
        this.end_success_simulation.setPromptText("Number between 0 to 1");
        this.end_warnings_after_success_simulation.setPromptText("Number between 0 to 1");
        task_CB.getItems().removeAll(task_CB.getItems());
        task_CB.getItems().addAll("Simulation Task", "Java Compiler Task");
        task_CB.getSelectionModel().select(0);
        random_simulation.getItems().removeAll(random_simulation.getItems());
        random_simulation.getItems().addAll("Yes", "No");
        random_simulation.getSelectionModel().select(0);
    }

    public void setMaxThreads(){
        thread_amount_CB.getItems().removeAll(thread_amount_CB.getItems());
        for(int i = 1;i <= this.appController.getMaxThreadsForTask();i++){
            thread_amount_CB.getItems().add(i);
        }
        thread_amount_CB.getSelectionModel().select(0);
    }

    private void setInvalidArguments(TextField textField, boolean set, BooleanProperty booleanProperty){
        if(set){
            this.error_label.setVisible(true);
            textField.getStyleClass().add("invalid_arguments");
            booleanProperty.set(false);
        } else{
            this.error_label.setVisible(false);
            textField.getStyleClass().removeAll("invalid_arguments");
            booleanProperty.set(true);
        }
    }

    @FXML
    void clickTask(ActionEvent event) {
        if(this.task_CB.getValue().equals("Simulation Task")){
            this.error_label.setVisible(true);
            this.isSimulation.set(true);
            this.next_btn.disableProperty().bind((this.processTimeValid.and(this.endSuccessValid.and(this.endWarningValid)).not()));
        }else {
            this.error_label.setVisible(false);
            this.isSimulation.set(false);
            this.next_btn.disableProperty().bind((this.srcFolderValid.and(this.compiledFolderValid)).not());
        }
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(TasksController mainControllers) {
        this.mainController = mainControllers;
    }

}
