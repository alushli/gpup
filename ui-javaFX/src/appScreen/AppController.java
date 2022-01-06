package appScreen;

import dtoObjects.GraphDTO;
import dtoObjects.SerialSetFXDTO;
import dtoObjects.TargetDTO;
import dtoObjects.TargetFXDTO;
import engineManager.EngineManager;
import enums.FxmlPath;
import enums.StyleSheetsPath;
import exceptions.XmlException;
import generalComponents.GeneralComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import menu.MenuController;
import target.Target;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AppController {
    @FXML private StackPane menu_area;
    private static Parent menuParent;
    private static MenuController menuComponentController = null;
    private Stage primaryStage;
    @FXML private StackPane content_area;
    private boolean isLoadFile = false;
    private EngineManager engineManager;
    private GeneralComponent generalComponent;
    private BooleanProperty isLight;
    private BooleanProperty isAnimation;

    @FXML
    private BorderPane main_screen;

    public AppController(){
        this.engineManager = new EngineManager();
        this.generalComponent = new GeneralComponent();
        this.generalComponent.setAppController(this);
        this.isLight = new SimpleBooleanProperty(true);
        this.isAnimation = new SimpleBooleanProperty(false);
        this.isLight.addListener((a,b,c)->{
            if(this.isLight.getValue()){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.APP_SCREEN_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.APP_SCREEN_LIGHT.toString());
            }else{
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.APP_SCREEN_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.APP_SCREEN_DARK.toString());
            }
        });
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

    public void setIsLight(boolean isLight) {
        this.isLight.set(isLight);
    }

    public BooleanProperty isLightProperty() {
        return isLight;
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
            URL url = getClass().getResource(FxmlPath.MENU.toString());
            fxmlLoader.setLocation(url);
            this.menuParent = fxmlLoader.load(url.openStream());
            this.menuComponentController = fxmlLoader.getController();
            this.menuComponentController.setAppController(this);
            this.menuComponentController.isLightProperty().bind(this.isLightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setArea(Parent data){
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(data);
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

    public void loadFile(String path) throws XmlException {
        this.engineManager.load(path);
    }

    public Collection<TargetFXDTO> getAllTargets(){
        return this.engineManager.getAllTargets();
    }

    public List<List<TargetDTO>> getTargetsPaths(String src, String des, String typeOfConnection){
        try {
            return this.engineManager.getTargetsPath(src, des, typeOfConnection);
        }catch (Exception e){
            System.out.println("Error in getTargetsPaths");
            return null;
        }
    }

    public LinkedHashSet<TargetDTO> getTargetCircle(String targetName) {
        try {
            return this.engineManager.getTargetCircle(targetName);
        }catch (Exception e){
            System.out.println("Error in getTargetCircle");
            return null;
        }
    }

    public GraphDTO getGraphInfo(){
        try{
            return this.engineManager.getGraphGeneralInfo();
        }catch (Exception e) {
            System.out.println("Error in getTargetCircle");
            return null;
        }
    }

    public TargetDTO getTargetInfo(String targetName){
        try{
            return this.engineManager.getTargetInfo(targetName);
        }catch (Exception e) {
            System.out.println("Error in getTargetInfo");
            return null;
        }
    }

    public void exportGraph(String path) throws IOException {
        this.engineManager.exportGraph(path);
    }

    public Set<SerialSetFXDTO> getGraphSerialSet(){
        return this.engineManager.getSerialSetOfGraph();
    }

    public boolean hasSerialSets(){
        return this.engineManager.hasSerialSet();
    }

    public Set<String> getTargetSerialSet(String target){
        return this.engineManager.getSerialSetOfTarget(target);
    }

    public Set<String> getWhatIfTargets(String name, String typeOfDependency){
        return this.engineManager.getWhatIfTargets(name, typeOfDependency);
    }

    public int getMaxThreadsForTask(){
        return this.engineManager.getMaxThreadsForTask();
    }

    public EngineManager getEngineManager() {
        return engineManager;
    }
}
