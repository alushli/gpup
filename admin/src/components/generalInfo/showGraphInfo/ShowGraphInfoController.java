package components.generalInfo.showGraphInfo;

import com.google.gson.Gson;
import components.appScreen.AppController;
import components.adminEnums.AppFxmlPath;
import components.generalComponents.targetsTable.TargetsTableController;
import components.generalInfo.GeneralInfoController;
import components.generalInfo.showGraphInfo.detailsGraphScreen.GraphInfoScreenController;
import dtoObjects.GeneralGraphInfoDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class ShowGraphInfoController extends components.mainControllers.Controllers{
    private GeneralInfoController mainController;
    private GraphInfoScreenController graphInfoScreenController;
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

    public GridPane getDetailsGrid() { return details_grid; }

    public StackPane getDataArea() {
        return data_area;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }

    public GeneralInfoController getMainController() {
        return mainController;
    }

    public void setPageScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.DETAILS_GRAPH_INFO_SCREEN.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.graphInfoScreenController = fxmlLoader.getController();
            this.graphInfoScreenController.setMainController(this);
            this.graphInfoScreenController.setAppController(this.appController);
            setPageLabels();
        } catch (Exception e){
            e.printStackTrace();
        }
        }


    private void setPageLabels(){
        String graphName = this.appController.getGraphName();
        String finalUrl = HttpUrl
                .parse(Constants.GRAPH_INFO)
                .newBuilder()
                .addQueryParameter("graphName", graphName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail in load general info");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                GeneralGraphInfoDTO generalGraphInfoDTO = gson.fromJson(response.body().string(), GeneralGraphInfoDTO.class);
                Platform.runLater(()->{
                    graphInfoScreenController.getGraph_name_label().setText(generalGraphInfoDTO.getName());
                    graphInfoScreenController.getTarget_num_label().setText(String.valueOf(generalGraphInfoDTO.getCountRoots() + generalGraphInfoDTO.getCountMiddles() + generalGraphInfoDTO.getCountLeaves() + generalGraphInfoDTO.getCountIndependents()));
                    graphInfoScreenController.getRoot_num_label().setText(String.valueOf(generalGraphInfoDTO.getCountRoots()));
                    graphInfoScreenController.getMid_num_label().setText(String.valueOf(generalGraphInfoDTO.getCountMiddles()));
                    graphInfoScreenController.getLeaf_num_label().setText(String.valueOf(generalGraphInfoDTO.getCountLeaves()));
                    graphInfoScreenController.getInd_num_label().setText(String.valueOf(generalGraphInfoDTO.getCountIndependents()));
                });

            }
        });
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.mainController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.965));
            this.targetsTableController.setSelectDisable();
        }catch (Exception e){

        }
    }
}
