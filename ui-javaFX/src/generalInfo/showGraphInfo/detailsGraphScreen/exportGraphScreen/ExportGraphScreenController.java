package generalInfo.showGraphInfo.detailsGraphScreen.exportGraphScreen;

import appScreen.AppController;
import enums.StyleSheetsPath;
import generalInfo.showGraphInfo.ShowGraphInfoController;
import generalInfo.showGraphInfo.detailsGraphScreen.GraphInfoScreenController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;


import javax.xml.soap.Text;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportGraphScreenController extends mainControllers.Controllers {
    private GraphInfoScreenController mainController;
    private BooleanProperty canExport;
    private BooleanProperty isLight;

    @FXML
    private StackPane main_screen;

    @FXML
    private Label folder_path_label;

    @FXML
    private TextField file_name_txt;

    @FXML
    private Button export_btn;

    @FXML
    public void initialize() {
        this.isLight = new SimpleBooleanProperty(true);
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
        this.isLight.addListener((a,b,c)->{
            if(this.isLight.getValue()){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.GENERAL_INFO_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.GENERAL_INFO_LIGHT.toString());
            }else{
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.GENERAL_INFO_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.GENERAL_INFO_DARK.toString());
            }
        });
    }

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    @FXML
    void clickExport(ActionEvent event) {
        Path path = Paths.get(this.folder_path_label.getText(),this.file_name_txt.getText());
        this.mainController.setFullPathExport(path.toString());
        this.mainController.exitPopup();
        this.mainController.exportGraph();
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
