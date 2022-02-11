package components.appScreen;

import components.menu.WorkerMenuController;
import components.workerEnums.AppFxmlPath;
import components.generalComponents.workerGeneralComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class AppController {
    @FXML private StackPane menu_area;
    private static Parent menuParent;
    private static WorkerMenuController menuComponentController = null;
    private Stage primaryStage;
    @FXML private StackPane content_area;
    private boolean isLoadFile = false;
    private workerGeneralComponent generalComponent;
    private String selectedTask;
    private boolean isSelectTask = false;
    private String userName;

    @FXML
    private BorderPane main_screen;

    public AppController(){
        this.generalComponent = new workerGeneralComponent();
        this.generalComponent.setAppController(this);
    }

    @FXML
    public void initialize() {
    }

    public void setMenu(){
        this.setMainMenu();
    }

    public void setMainMenu(){
        if (menuComponentController == null) {
            setMenuFxml();
        }
        setMenu(this.menuParent);
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
            this.menuComponentController.getName_label().setText(this.userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setArea(Parent data){
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(data);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setMenu(Parent data){
        menu_area.getChildren().removeAll();
        menu_area.getChildren().setAll(data);
    }

    public void setArea(StackPane area, Parent data){
        area.getChildren().removeAll();
        area.getChildren().setAll(data);
    }

    public WorkerMenuController getMenuComponentController() {
        return menuComponentController;
    }

    public void setSelectedTask(String selectedTask) {
        this.selectedTask = selectedTask;
    }

    public void setSelectTask(boolean selectTask) {
        isSelectTask = selectTask;
    }

}
