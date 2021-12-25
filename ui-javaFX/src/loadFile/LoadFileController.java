package loadFile;

import appScreen.AppController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class LoadFileController extends mainControllers.Controllers{
    @FXML
    private ImageView expansion_img;

    @FXML
    private Label load_message_label;

    @FXML
    private Label file_path_label;

    @FXML
    private GridPane what_next_area;

    @FXML
    void clickActions(ActionEvent event) {
        this.appController.getMenuComponentController().clickAction(event);
    }

    @FXML
    void clickGeneralInfo(ActionEvent event) {
        this.appController.getMenuComponentController().clickGeneralInfo(event);
    }

    @FXML
    void clickTasks(ActionEvent event) {
        this.appController.getMenuComponentController().clickTasks(event);
    }


    @FXML
    void clickLoadFile(ActionEvent event) {
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
        load_message_label.setText("yayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy yyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy yyyyyyyyyyyayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        load_message_label.getStyleClass().add("successes_message");
        load_message_label.getStyleClass().remove("failed_message");
    }

    void failedLoad(){
        this.appController.setLoadFile(false);
        what_next_area.setDisable(true);
        file_path_label.setText("");
        load_message_label.setText("faildddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        load_message_label.getStyleClass().add("failed_message");
        load_message_label.getStyleClass().remove("successes_message");
    }

    @FXML
    void clickExpansionMessage(ActionEvent event) {
        InputStream inStreamOpen = getClass().getResourceAsStream("expand_button.png");
        Image imageObjectOpen = new Image(inStreamOpen);
        InputStream inStreamClose = getClass().getResourceAsStream("minimize_button.png");
        Image imageObjectClose = new Image(inStreamClose);
        if(load_message_label.isWrapText()) {
            load_message_label.setWrapText(false);
            expansion_img.setImage(imageObjectOpen);
        }else{
            load_message_label.setWrapText(true);
            expansion_img.setImage(imageObjectClose);
        }
    }



    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

}
