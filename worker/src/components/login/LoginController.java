package components.login;

import general.Constant;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userName;

    @FXML
    private ComboBox<?> amountOfThreads;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessageLabel;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();


    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
    }

    @FXML
    void onLogin(ActionEvent event) {
        String userName = this.userName.getText();
        if(userName.isEmpty()){
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }
        String finalUrl = HttpUrl.parse(Constants.WORKER_LOGIN).newBuilder().
                addQueryParameter("userName", userName).
                addQueryParameter("numOfThreads", "5")
                .build().
                toString();

        Response response =  HttpClientUtil.runSync(finalUrl);
        if(response.code() == 200){
            this.errorMessageProperty.set("success");
        }else{
            this.errorMessageProperty.set("fail");
        }
    }




}
