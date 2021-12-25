package actions.showPaths.detailsScreen;

import actions.showPaths.ShowPathsController;
import appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PathsScreenController extends mainControllers.Controllers{
    private ShowPathsController mainController;

    @FXML
    private Label target1_label;

    @FXML
    private Label target2_label;

    @FXML
    private Label paths_message_label;

    @FXML
    private StackPane paths_SP;

    @FXML
    void clickSwitch(ActionEvent event) {
        String temp = target1_label.getText();
        target2_label.setText(target1_label.getText());
        target1_label.setText(temp);
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowPathsController mainControllers) {
        this.mainController = mainControllers;
    }

    void setPathsTable(int size){
        VBox vBox = new VBox();
        for(int i=0 ; i<size ; i++){
            Label label = new Label();
            label.setText(i+1 + "Please upload valid xml file first");
            vBox.getChildren().add(label);
        }
        paths_SP.getChildren().add(0,vBox);
    }
}
