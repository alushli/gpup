package components.appScreen;

import com.google.gson.Gson;
import components.adminEnums.AppFxmlPath;
import components.generalComponents.graphsTable.GraphFx;
import components.generalComponents.graphsTable.GraphTableController;
import components.generalComponents.targetsTable.TargetFX;
import components.menu.MenuController;
import components.generalComponents.GeneralComponent;
import dtoObjects.TargetDTO;
import dtoObjects.TargetFxCollection;
import dtoObjects.TargetFXDTO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class AppController {
    @FXML private StackPane menu_area;
    private static Parent menuParent;
    private static MenuController menuComponentController = null;
    private Stage primaryStage;
    @FXML private StackPane content_area;
    private boolean isLoadFile = false;
    private GeneralComponent generalComponent;
    private BooleanProperty isAnimation;
    private String graphName;
    private GraphFx graphFxSelected;
    private boolean isLoaded;
    private TargetDTO[] targetDTOS;
    private String selectedTask;
    private boolean isSelectTask = false;
    private String userName;
    private GraphTableController graphTableController = null;
    private Parent graphTableParent = null;

    public Parent getGraphTableParent() {
        return graphTableParent;
    }

    public void setGraphTableParent(Parent graphTableParent) {
        this.graphTableParent = graphTableParent;
    }

    public void setGraphTableController(GraphTableController graphTableController) {
        this.graphTableController = graphTableController;
    }

    public GraphTableController getGraphTableController() {
        return graphTableController;
    }

    public boolean isSelectTask() {
        return isSelectTask;
    }

    public void setSelectTask(boolean selectTask) {
        isSelectTask = selectTask;
    }

    public void setGraphFxSelected(GraphFx graphFxSelected) {
        this.graphFxSelected = graphFxSelected;
    }

    public GraphFx getGraphFxSelected() {
        return graphFxSelected;
    }

    public String getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(String selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Collection<TargetFX> getAllTargets(){
        Collection<TargetFX> targetFXES = new ArrayList<>();
        for (TargetDTO targetDTO : targetDTOS){
            targetFXES.add(new TargetFX(targetDTO));
        }
        return targetFXES;
    }

    @FXML
    private BorderPane main_screen;

    public AppController(){
        this.generalComponent = new GeneralComponent();
        this.generalComponent.setAppController(this);
        this.isAnimation = new SimpleBooleanProperty(false);
        this.isLoaded = false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @FXML
    public void initialize() {

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

    public void setMenu(){
        this.setMainMenu();
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

    //set graph name and update the targets collection
    public void setGraphName(String graphName1){
        this.isLoaded = false;
        String finalUrl = HttpUrl
                .parse(Constants.GRAPH_TARGETS)
                .newBuilder()
                .addQueryParameter("graphName", graphName1)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        isLoaded = false
                );
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                           isLoaded = false
                    );
                } else {
                    Platform.runLater(() -> {
                        try{
                            deserializeTargetFXCollection(response.body().string());
                            graphName = graphName1;
                            isLoaded = true;
                            isLoadFile = true;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    public String getGraphName() {
        return graphName;
    }

    private void deserializeTargetFXCollection(String jsonTargets){
        Gson gson = new Gson();
        this.targetDTOS = gson.fromJson(jsonTargets, TargetDTO[].class);
    }

}
