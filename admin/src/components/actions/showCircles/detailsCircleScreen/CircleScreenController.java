package components.actions.showCircles.detailsCircleScreen;

import com.google.gson.Gson;
import components.actions.showCircles.ShowCirclesController;
import components.appScreen.AppController;
import dtoObjects.TargetsPathDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

public class CircleScreenController extends components.mainControllers.Controllers {
    private ShowCirclesController mainController;

    @FXML
    private StackPane fall_screen_SP;

    @FXML
    private Label target_label;

    @FXML
    private Label paths_message_label;

    @FXML
    private TextArea paths_TA;

    @FXML
    private Button find_btn;


    @FXML
    void clickFind(ActionEvent event) {
        this.paths_TA.setText("");
        if(this.target_label.getText().equals("")){
            this.paths_TA.setText("Please select one target from the table.");
        } else {
            String graphName = this.appController.getGraphName();
            String finalUrl = HttpUrl
                    .parse(Constants. FIND_CIRCLE)
                    .newBuilder()
                    .addQueryParameter("graphName", graphName)
                    .addQueryParameter("targetName", this.target_label.getText())
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
                        TargetsPathDTO pathDTO = gson.fromJson(json,TargetsPathDTO.class);
                        setCircle(pathDTO);
                    });
                }
            });
        }
    }

    private void setCircle(TargetsPathDTO pathDTO){
        List<String> targets = pathDTO.getTargets();
        if(targets.size() == 0)
            this.paths_TA.setText("There is no circle with the selected target.");
        else {
            this.paths_TA.appendText("( ");
            for (String targetDTO : targets) {
                this.paths_TA.appendText(targetDTO + " ");
            }
            this.paths_TA.appendText(")");
        }
    }

    public TextArea getPaths_TA() {
        return paths_TA;
    }

    public Button getFind_btn() {
        return find_btn;
    }

    public Label getTarget_label() {
        return target_label;
    }

    public StackPane getFall_screen_SP() {
        return fall_screen_SP;
    }

    @Override
    public void setAppController(AppController mainControllers) { this.appController = mainControllers; }

    public void setMainController(ShowCirclesController mainControllers) {
        this.mainController = mainControllers;
    }

}
