package components.login;

import components.appScreen.AppController;
import components.workerEnums.AppFxmlPath;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.workerEngine.WorkerEngine;
import okhttp3.HttpUrl;
import okhttp3.Response;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

public class LoginController {
    private Stage primaryStage;

    @FXML
    private Label login_header;

    @FXML
    private Label user_label;

    @FXML
    private TextField userName;

    @FXML
    private Label threads_label;

    @FXML
    private ComboBox<Integer> amountOfThreads;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label welcome_header;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        amountOfThreads.getItems().removeAll(amountOfThreads.getItems());
        amountOfThreads.getItems().addAll(1,2,3,4,5);
        amountOfThreads.getSelectionModel().select(0);
    }

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    @FXML
    void onLogin(ActionEvent event) {
        String userName = this.userName.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            this.errorMessageLabel.getStyleClass().remove("successes_message");
            this.errorMessageLabel.getStyleClass().add("failed_message");
            return;
        }
        String finalUrl = HttpUrl.parse(Constants.WORKER_LOGIN).newBuilder().
                addQueryParameter("userName", userName).
                addQueryParameter("numOfThreads", amountOfThreads.getValue().toString())
                .build().
                toString();

        Response response = HttpClientUtil.runSync(finalUrl);
        if (response != null && response.code() == 200) {
            this.errorMessageProperty.set("");
            this.errorMessageLabel.getStyleClass().remove("failed_message");
            setAppScreen();
        } else {
            this.errorMessageLabel.getStyleClass().remove("successes_message");
            this.errorMessageLabel.getStyleClass().add("failed_message");
            if (response != null && response.code() == 401 )
                this.errorMessageProperty.set("User already exists in the system");
            else
                this.errorMessageProperty.set("Something went wrong");
        }
    }

    private void setAppScreen(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.APP_SCREEN.toString());
            fxmlLoader.setLocation(url);
            Parent rootContainer = fxmlLoader.load(url.openStream());
            AppController appController = fxmlLoader.getController();
            appController.setUserName(this.userName.getText());
            appController.setThreadsAmount(amountOfThreads.getValue());
            appController.setWorkerEngine(new WorkerEngine(this.userName.getText(),amountOfThreads.getValue()));
            appController.setMenu();
            appController.setPrimaryStage(primaryStage);
            Stage appWindow;
            Scene secondScene = new Scene(rootContainer, 1300, 750);
            appWindow = new Stage();
            appWindow.setScene(secondScene);
            appWindow.setX(this.primaryStage.getX());
            appWindow.setY(this.primaryStage.getY());
            appWindow.setTitle("GPUP");
            appWindow.show();
            this.primaryStage.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
