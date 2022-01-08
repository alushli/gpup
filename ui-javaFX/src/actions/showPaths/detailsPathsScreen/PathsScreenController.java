package actions.showPaths.detailsPathsScreen;

import actions.showPaths.ShowPathsController;
import appScreen.AppController;
import dtoObjects.TargetDTO;
import enums.StyleSheetsPath;
import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class PathsScreenController extends mainControllers.Controllers{
    private ShowPathsController mainController;
    private StringProperty skin;
    private BooleanProperty isAnimation;
    private Image switchDarkImg;
    private Image switchLightImg;

    @FXML
    private ImageView switch_img;

    @FXML
    private Button find_btn;

    @FXML
    private Label target1_label;

    @FXML
    private Label target2_label;

    @FXML
    private Label paths_message_label;

    @FXML
    private ComboBox<String> direction_CB;

    @FXML
    private TextArea paths_TA;

    @FXML
    private StackPane fall_screen_SP;

    public StringProperty skinProperty() {
        return skin;
    }

    public BooleanProperty isAnimationProperty() {
        return isAnimation;
    }

    @FXML
    public void initialize() {
        this.switchDarkImg = new Image("/actions/showPaths/detailsPathsScreen/switch_icon_dark.png");
        this.switchLightImg = new Image("/actions/showPaths/detailsPathsScreen/switch_icon_light.png");
        direction_CB.getItems().removeAll(direction_CB.getItems());
        direction_CB.getItems().addAll("Depends On", "Required For");
        direction_CB.getSelectionModel().select(0);
        this.skin = new SimpleStringProperty("Light");
        this.isAnimation = new SimpleBooleanProperty(false);
    }

    public void skinListener(){
        this.mainController.getMainController().skinListener(this.skin, this.fall_screen_SP);
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    public TextArea getPaths_TA() {
        return paths_TA;
    }

    @FXML
    void clickFind(ActionEvent event) {
        String direction;
        if(this.direction_CB.getValue().equals("Depends On"))
            direction = "D";
        else
            direction = "R";
        this.paths_TA.setText("");
        if(this.target1_label.getText().equals("") || this.target2_label.getText().equals("")){
            this.paths_TA.setText("Please select two targets from the table.");
        } else {
            List<List<TargetDTO>> list = this.appController.getTargetsPaths(this.target1_label.getText(), this.target2_label.getText(), direction);
            setPathsTable(list);
        }
    }

    public Label getTarget1_label() {
        return target1_label;
    }

    public Label getTarget2_label() {
        return target2_label;
    }

    public Button getFind_btn() {
        return find_btn;
    }

    @FXML
    void clickSwitch(ActionEvent event) {
        if(this.isAnimation.getValue().booleanValue()){
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(1500),this.switch_img);
            rotateTransition.setByAngle(720);
            rotateTransition.play();
        }
        String temp = target2_label.getText();
        target2_label.setText(target1_label.getText());
        target1_label.setText(temp);
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowPathsController mainControllers) {
        this.mainController = mainControllers;
    }

    void setPathsTable(List<List<TargetDTO>> list){
        int index = 1;
        if(list.isEmpty())
            this.paths_TA.setText("There is no path between the targets on the selected dependency.");
        else{
            for(List<TargetDTO> targetDTOList : list){
                this.paths_TA.appendText(index + ". ");
                int j =1;
                for (TargetDTO targetDTO : targetDTOList){
                    if(j == targetDTOList.size())
                        this.paths_TA.appendText(targetDTO.getName());
                    else
                        this.paths_TA.appendText(targetDTO.getName()+"->");
                    j++;
                }
                index++;
                this.paths_TA.appendText("\n");
            }
        }
    }
}
