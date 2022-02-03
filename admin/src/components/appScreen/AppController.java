package components.appScreen;

import components.adminEnums.AppFxmlPath;
import components.menu.MenuController;
import components.generalComponents.GeneralComponent;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;

public class AppController {
    @FXML private StackPane menu_area;
    private static Parent menuParent;
    private static MenuController menuComponentController = null;
    private Stage primaryStage;
    @FXML private StackPane content_area;
    private boolean isLoadFile = false;
    private GeneralComponent generalComponent;
    private BooleanProperty isAnimation;

    @FXML
    private BorderPane main_screen;

    public AppController(){
        this.generalComponent = new GeneralComponent();
        this.generalComponent.setAppController(this);
        this.isAnimation = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        setMainMenu();
    }

    public void setMainMenu(){
        if (menuComponentController == null) {
            setMenuFxml();
        }
        setMenu(this.menuParent);
    }

    public BooleanProperty isAnimationProperty() {
        return isAnimation;
    }

    public void setIsAnimation(boolean isAnimation) {
        this.isAnimation.set(isAnimation);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setMenuFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.MENU.toString());
            fxmlLoader.setLocation(url);
            this.menuParent = fxmlLoader.load(url.openStream());
            this.menuComponentController = fxmlLoader.getController();
            this.menuComponentController.setAppController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setArea(Parent data){
        FadeTransition ft = null;
        if(this.isAnimation.getValue()) {
            ft = new FadeTransition(Duration.millis(1500), data);
            ft.setToValue(1.0);
            ft.setFromValue(0.0);
        }
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(data);
        if(this.isAnimation.getValue() && ft != null) {
            ft.play();
        }
    }

    public void setMenu(Parent data){
        menu_area.getChildren().removeAll();
        menu_area.getChildren().setAll(data);
    }

    public void setArea(StackPane area, Parent data){
        area.getChildren().removeAll();
        area.getChildren().setAll(data);
    }

    public MenuController getMenuComponentController() {
        return menuComponentController;
    }

    public void setLoadFile(boolean val){
        this.isLoadFile = val;
    }

    public boolean getLoadFile(){
        return isLoadFile;
    }
}
