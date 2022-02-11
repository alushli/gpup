package components.actions.showPaths.detailsPathsScreen;

import com.google.gson.Gson;
import components.actions.showPaths.ShowPathsController;
import components.appScreen.AppController;
import dtoObjects.TargetsPathDTO;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;

public class PathsScreenController extends components.mainControllers.Controllers{
    private ShowPathsController mainController;
    private BooleanProperty isAnimation;
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

    public BooleanProperty isAnimationProperty() {
        return isAnimation;
    }

    @FXML
    public void initialize() {
        this.switchLightImg = new Image("/components/actions/showPaths/detailsPathsScreen/switch_icon.png");
        direction_CB.getItems().removeAll(direction_CB.getItems());
        direction_CB.getItems().addAll("Depends On", "Required For");
        direction_CB.getSelectionModel().select(0);
        this.isAnimation = new SimpleBooleanProperty(false);
        this.switch_img.setImage(this.switchLightImg);
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
            String graphName = this.appController.getGraphName();
            String finalUrl = HttpUrl
                    .parse(Constants. FIND_PATHS)
                    .newBuilder()
                    .addQueryParameter("graphName", graphName)
                    .addQueryParameter("dependencyType", direction)
                    .addQueryParameter("from", this.target1_label.getText())
                    .addQueryParameter("to",  this.target2_label.getText())
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("Failed on find circle.");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String json = response.body().string();
                    Platform.runLater(()->{
                        Gson gson = new Gson();
                        TargetsPathDTO pathDTO[] = gson.fromJson(json,TargetsPathDTO[].class);
                        setPathsTable(pathDTO);
                    });
                }
            });
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

    private void setPathsTable(TargetsPathDTO[] pathsTable){
        if(pathsTable.length == 0){
            this.paths_TA.setText("There is no path between the targets on the selected dependency.");
        }else{
            for (TargetsPathDTO pathDTO : pathsTable){
                int i = 0;
                for (String target : pathDTO.getTargets()){
                    if(i==pathDTO.getTargets().size() - 1){
                        this.paths_TA.appendText(target);
                    }else{
                        this.paths_TA.appendText(target+"->");
                    }
                    i++;
                }
                this.paths_TA.appendText("\n");
            }
        }

    }
    
}
