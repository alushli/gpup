package loadFile;

import appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.File;


public class LoadFileController extends mainControllers.Controllers{
    @FXML
    private TextArea load_message_ta;

    @FXML
    private Label file_path_label;

    @FXML
    private GridPane what_next_area;

    @FXML
    private Button show_target_info_label;

    @FXML
    private Button show_graph_info_label;

    @FXML
    private Button find_path_label;

    @FXML
    private Button find_circle_label;

    @FXML
    private Button simulation_label;

    @FXML
    private Button compiler_label;


    @FXML
    void clickActions(ActionEvent event) {
        setVisibleButtons();
        this.find_path_label.setVisible(true);
        this.find_circle_label.setVisible(true);
        this.appController.getMenuComponentController().clickAction(event);
    }

    @FXML
    void clickGeneralInfo(ActionEvent event) {
        setVisibleButtons();
        this.show_graph_info_label.setVisible(true);
        this.show_target_info_label.setVisible(true);
        this.appController.getMenuComponentController().clickGeneralInfo(event);
    }

    @FXML
    void clickTasks(ActionEvent event) {
        setVisibleButtons();
        this.simulation_label.setVisible(true);
        this.compiler_label.setVisible(true);
        this.appController.getMenuComponentController().clickTasks(event);
    }

    private void setVisibleButtons(){
        this.show_graph_info_label.setVisible(false);
        this.show_target_info_label.setVisible(false);
        this.find_path_label.setVisible(false);
        this.find_circle_label.setVisible(false);
        this.simulation_label.setVisible(false);
        this.compiler_label.setVisible(false);
    }

    @FXML
    void clickCompiler(ActionEvent event) {
        this.appController.getMenuComponentController().getTasksController().setCompilerControllers();
        setVisibleButtons();
    }

    @FXML
    void clickSimulation(ActionEvent event) {
        this.appController.getMenuComponentController().getTasksController().setSimulationControllers();
        setVisibleButtons();
    }

    @FXML
    void clickFindCircle(ActionEvent event) {
        this.appController.getMenuComponentController().getActionController().setShowCirclesControllers();
        setVisibleButtons();
    }

    @FXML
    void clickFindPath(ActionEvent event) {
        this.appController.getMenuComponentController().getActionController().setShowPathsControllers();
        setVisibleButtons();
    }

    @FXML
    void clickTargetInfo(ActionEvent event) {
        this.appController.getMenuComponentController().getGeneralInfoController().setTargetInfoControllers();
        setVisibleButtons();
    }

    @FXML
    void clickGraphInfo(ActionEvent event) {
        this.appController.getMenuComponentController().getGeneralInfoController().setGraphInfoControllers();
        setVisibleButtons();
    }

    @FXML
    void clickLoadFile(ActionEvent event) {
        setVisibleButtons();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            failedLoad();
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        file_path_label.setText(absolutePath);
        successLoad();

    }

    void successLoad(){
        this.appController.setLoadFile(true);
        what_next_area.setDisable(false);
        load_message_ta.setText("yayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy yyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy yyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        load_message_ta.getStyleClass().add("successes_message");
        load_message_ta.getStyleClass().remove("failed_message");
    }

    void failedLoad(){
        this.appController.setLoadFile(false);
        what_next_area.setDisable(true);
        file_path_label.setText("");
        load_message_ta.setText("faildddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        load_message_ta.getStyleClass().add("failed_message");
        load_message_ta.getStyleClass().remove("successes_message");
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

}
