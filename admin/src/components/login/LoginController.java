package components.login;

import components.appScreen.AppController;
import components.adminEnums.AppFxmlPath;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
    private Label welcome_header;

    @FXML
    private Label user_label;

    @FXML
    private TextField userName;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Button loginButton;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
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
        String finalUrl = HttpUrl.parse(Constants.ADMIN_LOGIN).newBuilder().
                addQueryParameter("userName", userName)
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
            else{
                if(response != null){
                    this.errorMessageProperty.set("Something went wrong" + response.code());
                }else{
                    this.errorMessageProperty.set("Something went wrong" + " response null!");
                }
            }

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
