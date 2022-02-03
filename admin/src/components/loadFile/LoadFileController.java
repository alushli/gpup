package components.loadFile;

import components.appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class LoadFileController extends components.mainControllers.Controllers{
    private static String workFile = null;

    @FXML
    private StackPane main_screen;

    @FXML
    private Button load_btn;

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

    public void checkTaskRun(){
//        if(this.appController.getEngineManager().isTaskRun() == true)
//            this.load_btn.setDisable(true);
//        else
//            this.load_btn.setDisable(false);
    }

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
        this.appController.setMainMenu();
        this.appController.getMenuComponentController().clickTasks(event);

    }

    private void setVisibleButtons(){
        this.show_graph_info_label.setVisible(false);
        this.show_target_info_label.setVisible(false);
        this.find_path_label.setVisible(false);
        this.find_circle_label.setVisible(false);
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
//        try {
//            setVisibleButtons();
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Select xml file");
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
//            File selectedFile = fileChooser.showOpenDialog(null);
//            if (selectedFile == null) {
//                failedLoad("Please choose an xml file");
//                return;
//            }
//            String absolutePath = selectedFile.getAbsolutePath();
//            this.appController.loadFile(absolutePath);
//            workFile = absolutePath;
//            file_path_label.setText(workFile);
//            successLoad();
//        } catch (XmlException e) {
//            failedLoad(e.getMessage());
//            file_path_label.setText(workFile);
//        }
    }

    void successLoad(){
        this.appController.setLoadFile(true);
        what_next_area.setDisable(false);
        load_message_ta.setText("The xml was uploaded successfully");
        load_message_ta.getStyleClass().remove("failed_message");
        load_message_ta.getStyleClass().add("successes_message");
    }

    void failedLoad(String error){
        String message;
        if(workFile != null){
            message = "File load failed - previous file saved.\n";
        }else{
            message = "";
        }
        load_message_ta.setText(message + "Errors:\n" + error);
        load_message_ta.getStyleClass().remove("successes_message");
        load_message_ta.getStyleClass().add("failed_message");
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

}
