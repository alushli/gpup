package components.loadFile;

import components.appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import okhttp3.*;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.File;


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

    public void checkTaskRun(){
//        if(this.appController.getEngineManager().isTaskRun() == true)
//            this.load_btn.setDisable(true);
//        else
//            this.load_btn.setDisable(false);
    }



    @FXML
    void clickLoadFile(ActionEvent event) {
        String RESOURCE = "/upload-file";
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select xml file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                failedLoad("Please choose an xml file");
                return;
            }
            String absolutePath = selectedFile.getAbsolutePath();
            File file = new File(absolutePath);
            RequestBody body =
                    new MultipartBody.Builder()
                            .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/plain")))
                            .build();
            Request request = new Request.Builder()
                    .url(Constants.FULL_SERVER_PATH + RESOURCE)
                    .post(body)
                    .build();

            Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
            Response response = call.execute();
            if (response != null && response.code() == 200) {
                workFile = absolutePath;
                file_path_label.setText(workFile);
                successLoad();
            } else {
                if (response != null && response.code() == 401 ) {
                    failedLoad(response.body().string());
                    file_path_label.setText(workFile);
                }
                else {
                    failedLoad(response.body().string());
                    file_path_label.setText(workFile);
                }
            }
        } catch (Exception e) {
            failedLoad(e.getMessage());
            file_path_label.setText(workFile);
        }
    }

    void successLoad(){
        //this.appController.setLoadFile(true);
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
