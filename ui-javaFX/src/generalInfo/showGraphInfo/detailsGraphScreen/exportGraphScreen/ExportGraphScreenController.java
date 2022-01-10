package generalInfo.showGraphInfo.detailsGraphScreen.exportGraphScreen;

import appScreen.AppController;
import enums.StyleSheetsPath;
import generalInfo.showGraphInfo.detailsGraphScreen.GraphInfoScreenController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportGraphScreenController extends mainControllers.Controllers {
    private GraphInfoScreenController mainController;
    private BooleanProperty canExport;
    private StringProperty skin;

    @FXML
    private StackPane main_screen;

    @FXML
    private Label folder_path_label;

    @FXML
    private TextField file_name_txt;

    @FXML
    private Button export_btn;

    @FXML
    private Button select_folder_btn;

    @FXML
    private Label export_message;

    @FXML
    private VBox export_VB;

    @FXML
    private Label full_path_label;

    @FXML
    private ImageView graph_img;

    @FXML
    public void initialize() {
        this.export_VB.setVisible(false);
        this.skin = new SimpleStringProperty("Light");
        this.canExport = new SimpleBooleanProperty();
        this.export_btn.disableProperty().bind(canExport.not());
        this.file_name_txt.textProperty().addListener((a,b,c)->{
            if(file_name_txt.getText().length() >0 && folder_path_label.getText().length() > 0){
                this.canExport.set(true);
            }else{
                this.canExport.set(false);
            }
        });
        this.folder_path_label.textProperty().addListener((a,b,c)->{
            if(file_name_txt.getText().length() >0 && folder_path_label.getText().length() > 0){
                this.canExport.set(true);
            }else{
                this.canExport.set(false);
            }
        });
    }

    public void skinListener(){
        this.mainController.skinListener(this.skin, this.main_screen);
    }

    public StringProperty skinProperty() {
        return skin;
    }

    @FXML
    void clickExport(ActionEvent event) {
        Path path = Paths.get(this.folder_path_label.getText(),this.file_name_txt.getText().replaceAll("\\s+",""));
        try {
            this.mainController.getAppController().exportGraph(path.toString());
            this.export_message.setText("The graph export successfully");
            this.export_btn.disableProperty().unbind();
            this.export_btn.setDisable(true);
            this.select_folder_btn.setDisable(true);
            this.export_VB.setVisible(true);
            this.full_path_label.setText(path + ".png");
            this.graph_img.setImage(new Image(Paths.get(path + ".png").toUri().toString()));
        } catch (Exception e){
            this.export_message.setText("The graph doesn't export. Please try again");
        }
    }

    @FXML
    void clickSelect(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder to export to");
        File selectedDirectory = directoryChooser.showDialog(this.mainController.getPopupWindow());
        if (selectedDirectory == null) {
            return;
        }
        String absolutePath = selectedDirectory.getAbsolutePath();
        folder_path_label.setText(absolutePath);
    }


    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GraphInfoScreenController mainControllers) {
        this.mainController = mainControllers;
    }
}
