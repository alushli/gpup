import components.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.FxmlPath;
import worker.servlets.Login;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(FxmlPath.LOGIN.toString());
        fxmlLoader.setLocation(url);
        Parent rootContainer = fxmlLoader.load(url.openStream());
        LoginController loginController = fxmlLoader.getController();
        primaryStage.setScene(new Scene(rootContainer, 1300, 750));
        primaryStage.setTitle("GPUP");
        primaryStage.show();
    }
}
