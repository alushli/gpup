package generalInfo.showTargetInfo;

import appScreen.AppController;
import dtoObjects.TargetDTO;
import enums.FxmlPath;
import enums.StyleSheetsPath;
import generalComponents.targetsTable.TargetsTableController;
import generalInfo.GeneralInfoController;
import generalInfo.showTargetInfo.detailsTargetScreen.TargetInfoScreenController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scema.generated.GPUPTarget;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class ShowTargetInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;
    private IntegerProperty curSelectedCount;
    private TargetInfoScreenController targetInfoScreenController;
    private TargetsTableController targetsTableController;
    private BooleanProperty isLight;

    @FXML
    private StackPane main_screen;

    @FXML
    private StackPane data_area;

    @FXML
    private GridPane details_grid;

    @FXML
    private VBox table_VB;

    @FXML
    private StackPane table_SP;

    @FXML
    private StackPane page_SP;

    @FXML
    public void initialize() {
        this.isLight = new SimpleBooleanProperty(true);
        this.isLight.addListener((a,b,c)->{
            if(this.isLight.getValue()){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.GENERAL_INFO_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.GENERAL_INFO_LIGHT.toString());
            }else{
                this.main_screen.getStylesheets().remove(StyleSheetsPath.MAIN_CSS_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.GENERAL_INFO_LIGHT.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.MAIN_CSS_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.GENERAL_INFO_DARK.toString());
            }
        });
    }

    public BooleanProperty isLightProperty() {
        return isLight;
    }

    public StackPane getDataArea() { return data_area; }

    public GridPane getDetailsGrid() { return details_grid; }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }


    public void setPageScreen(){
        try{
            this.curSelectedCount = new SimpleIntegerProperty();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.DETAILS_TARGET_INFO_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.targetInfoScreenController = fxmlLoader.getController();
            this.targetInfoScreenController.setMainController(this);
            this.targetInfoScreenController.setAppController(this.appController);
            setPageLabels();
        } catch (Exception e){
        }
    }

    private void setPageLabels(){
        this.curSelectedCount.bind(this.targetsTableController.selectedCounterProperty());
        this.curSelectedCount.addListener((a,b,c)->{
            if(curSelectedCount.getValue() == 0){
              this.targetInfoScreenController.getTarget_info_grid().setVisible(false);
              this.targetInfoScreenController.getError_message().setVisible(true);
                resetTargetInfo();
            } else{
                this.targetInfoScreenController.getError_message().setVisible(false);
                this.targetInfoScreenController.getTarget_info_grid().setVisible(true);
                setTargetInfo(this.appController.getTargetInfo(this.targetsTableController.getCurSelected().get(0).getName()));
            }
        });
    }

    private void resetTargetInfo(){
        this.targetInfoScreenController.getTarget_name_label().setText("");
        this.targetInfoScreenController.getFree_text_TA().setText("");
        this.targetInfoScreenController.getPosition_label().setText("");
        this.targetInfoScreenController.getDirect_DO_list().getItems().clear();
        this.targetInfoScreenController.getDirect_RF_list().getItems().clear();
    }

    private void setTargetInfo(TargetDTO target){
        this.targetInfoScreenController.getTarget_name_label().setText(target.getName());
        this.targetInfoScreenController.getFree_text_TA().setText(target.getGeneralInfo());
        this.targetInfoScreenController.getPosition_label().setText(target.getPosition().toString());
        setTargetList(this.targetInfoScreenController.getDirect_DO_list(),target.getDependsOnList());
        setTargetList(this.targetInfoScreenController.getDirect_RF_list(),target.getRequiredForList());
    }

    private void setTargetList(ListView<String> listView, Set<String> list){
        for(String target : list){
            listView.getItems().add(target);
        }
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(FxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));
            this.targetsTableController.setMaxSelect(1);
            this.targetsTableController.isLightProperty().bind(this.appController.isLightProperty());
        }catch (Exception e){

        }
    }
}
