package components.generalInfo.showTargetInfo;

import com.google.gson.Gson;
import components.appScreen.AppController;
import components.adminEnums.AppFxmlPath;
import components.generalComponents.targetsTable.TargetFX;
import components.generalComponents.targetsTable.TargetsTableController;
import components.generalInfo.GeneralInfoController;
import components.generalInfo.showTargetInfo.detailsTargetScreen.TargetInfoScreenController;
import dtoObjects.GeneralGraphInfoDTO;
import dtoObjects.TargetDTO;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class ShowTargetInfoController extends components.mainControllers.Controllers{
    private GeneralInfoController mainController;
    private IntegerProperty curSelectedCount;
    private TargetInfoScreenController targetInfoScreenController;
    private TargetsTableController targetsTableController;

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
    void clickDeselectAll(ActionEvent event) {
        this.targetsTableController.deselectAll();
    }

    public GeneralInfoController getMainController() {
        return mainController;
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
            URL url = getClass().getResource(AppFxmlPath.DETAILS_TARGET_INFO_SCREEN.toString());
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
                TargetFX targetFX = this.targetsTableController.getCurSelected().get(0);
                setTargetInfo(targetFX);
                //setTargetInfo(this.appController.getTargetInfo(this.targetsTableController.getCurSelected().get(0).getName()));
            }
        });
    }

    private void resetTargetInfo(){
        this.targetInfoScreenController.getTarget_name_label().setText("");
        this.targetInfoScreenController.getFree_text_TA().setText("");
        this.targetInfoScreenController.getPosition_label().setText("");
        this.targetInfoScreenController.getDirect_DO_list().getItems().clear();
        this.targetInfoScreenController.getDirect_RF_list().getItems().clear();
        this.targetInfoScreenController.getTotal_DO_list().getItems().clear();
        this.targetInfoScreenController.getTotal_RF_list().getItems().clear();
    }

    private void setTargetInfo(TargetFX target){
        String graphName = this.appController.getGraphName();
        String finalUrl = HttpUrl
                .parse(Constants.TARGET_INFO)
                .newBuilder()
                .addQueryParameter("graphName", graphName)
                .addQueryParameter("targetName", target.getName())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail in load target info");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                TargetDTO targetDTO = gson.fromJson(response.body().string(), TargetDTO.class);
                String name = targetDTO.getName();
                Platform.runLater(()->{
                    targetInfoScreenController.getTarget_name_label().setText(name);
                    targetInfoScreenController.getFree_text_TA().setText(targetDTO.getGeneralInfo());
                    targetInfoScreenController.getPosition_label().setText(targetDTO.getPosition());
                    setTargetList(targetInfoScreenController.getDirect_DO_list(),targetDTO.getDependsOnList());
                    setTargetList(targetInfoScreenController.getDirect_RF_list(),targetDTO.getRequiredForList());
                    setTargetList(targetInfoScreenController.getTotal_DO_list(),targetDTO.getTotalDependsOn());
                    setTargetList(targetInfoScreenController.getTotal_RF_list(),targetDTO.getRequiredForList());
                });
            }
        });

    }

    private void setTargetList(ListView<String> listView, Set<String> list){
        for(String target : list){
            listView.getItems().add(target);
        }
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));
            this.targetsTableController.setMaxSelect(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
