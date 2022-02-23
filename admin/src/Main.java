import components.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.FxmlPath;
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
        loginController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(rootContainer, 650, 350));
        primaryStage.resizableProperty().set(false);
        primaryStage.setTitle("GPUP");
        primaryStage.show();
    }

}
