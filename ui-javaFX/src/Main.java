import appScreen.AppController;
import enums.FxmlPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(FxmlPath.APP_SCREEN.toString());
        fxmlLoader.setLocation(url);
        Parent rootContainer = fxmlLoader.load(url.openStream());
        AppController appController = fxmlLoader.getController();
        appController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(rootContainer, 1300, 750));
        primaryStage.setTitle("GPUP");
        primaryStage.getIcons().add(new Image("/resources/logo.png"));
        primaryStage.show();
    }
}
