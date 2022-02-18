package components.tasks;

import com.google.gson.Gson;
import components.adminEnums.AppFxmlPath;
import components.adminEnums.SimulationEntryPointAdmin;
import components.appScreen.AppController;
import components.generalComponents.targetsTable.TargetFX;
import components.generalComponents.targetsTable.TargetsTableController;
import components.tasks.runTaskScreen.RunTaskController;
import components.tasks.runTaskScreen.SelectTargetController;
import components.tasks.runTaskScreen.SelectTaskScreenController;
import dtoObjects.NewCompilationTaskDetails;
import dtoObjects.NewSimulationTaskDetails;
import dtoObjects.TargetFXDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import okhttp3.*;
import utils.Constants;
import utils.HttpClientUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class CreateNewTasksController extends components.mainControllers.Controllers{
    private TargetsTableController targetsTableController;
    private SelectTargetController selectTargetController;
    private SelectTaskScreenController selectTaskScreenController;
    private RunTaskController runTaskController;
    private boolean isWhatIf = false;
    private ArrayList<TargetFX> selectedTargets;
    private String whatIfDirection;
    private int threadsNum;
    private String graphName;
    private String taskName;

    @FXML
    private Button select_all_btn;

    @FXML
    private Button deselect_all_btn;

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
        this.targetsTableController.setWhatIfHappened(false);
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setThreadsNum(int threadsNum) {
        this.threadsNum = threadsNum;
    }

    public ArrayList<TargetFX> getSelectedTargets() {
        return selectedTargets;
    }

    @FXML
    void clickSelectAll(ActionEvent event) {
        this.targetsTableController.SelectAll();
    }

    public StackPane getData_area() {
        return data_area;
    }

    public GridPane getDetails_grid() {
        return details_grid;
    }

    public void setWhatIf(boolean whatIf) {
        isWhatIf = whatIf;
    }

    public boolean isWhatIf() {
        return isWhatIf;
    }

    @FXML
    public void initialize() {
        this.whatIfDirection = "dependsOn";
        this.selectedTargets = new ArrayList<>();
    }

    public void updateTaskName(String name){
        this.runTaskController.getTask_name_label().setText(name);
        this.runTaskController.setTaskType(name);
    }


    public void updateSimulationTaskProperties(int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPointAdmin entryPoint){
        Platform.runLater(()->{
            Collection<String> targets = getTargetsToRun(this.selectedTargets);
            int i = 0;

            String targetsArr[] = new String[targets.size()];
            for (String target : targets){
                targetsArr[i] = target;
                i++;
            }

            NewSimulationTaskDetails newSimulationTaskDetails = new NewSimulationTaskDetails(this.taskName,"simulation",this.appController.getGraphName(),targetsArr,
                    processTime, isRandom, chanceTargetSuccess, chanceTargetWarning);
            Gson gson = new Gson();
            String body = "task=" + gson.toJson(newSimulationTaskDetails);

            String finalUrl = HttpUrl.parse(Constants.CREATE_NEW_TASK).newBuilder().
                    addQueryParameter("graphName", this.appController.getGraphName())
                    .addQueryParameter("taskType", "simulation")
                    .build().
                    toString();

            Request request = new Request.Builder().url(finalUrl).
                    post(RequestBody.create(body.getBytes())).build();

            Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
            try{
                Response response = call.execute();
                if(response.code() != 200){
                    this.selectTaskScreenController.getSave_error_label().setText("something went wrong...");
                }else{
                    this.appController.getMenuComponentController().getTaskController().setTasksManagementControllers();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void updateCompilerTaskProperties(String sourceFolder, String productFolder, SimulationEntryPointAdmin entryPoint){
        Platform.runLater(()->{
            String newSourceFolder = sourceFolder.replace("\\", "\\\\");
            String newProductFolder = productFolder.replace("\\", "\\\\");

            Collection<String> targets = getTargetsToRun(this.selectedTargets);
            int i = 0;

            String targetsArr[] = new String[targets.size()];
            for (String target : targets){
                targetsArr[i] = target;
                i++;
            }

            NewCompilationTaskDetails newCompilationTaskDetails = new NewCompilationTaskDetails(this.taskName,"compilation",this.appController.getGraphName(),targetsArr,newSourceFolder, newProductFolder);
            Gson gson = new Gson();
            String body = "task=" + gson.toJson(newCompilationTaskDetails);

            String finalUrl = HttpUrl.parse(Constants.CREATE_NEW_TASK).newBuilder().
                    addQueryParameter("graphName", this.appController.getGraphName())
                    .addQueryParameter("taskType", "compilation")
                    .build().
                    toString();

            Request request = new Request.Builder().url(finalUrl).
                    post(RequestBody.create(body.getBytes())).build();

            Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
            try{
                Response response = call.execute();
                if(response.code() != 200){
                    this.selectTaskScreenController.getSave_error_label().setText("something went wrong...");
                }else{
                    this.appController.getMenuComponentController().getTaskController().setTasksManagementControllers();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }



    private Collection<String> getTargetsToRun(ArrayList<TargetFX> targetFXDTOS){
        Collection<String> targets = new HashSet<>();
        for(TargetFX targetFXDTO: targetFXDTOS){
            targets.add(targetFXDTO.getName());
        }
        return targets;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setSelectedTargets(ArrayList<TargetFX> selectedTargets) {
        this.selectedTargets = selectedTargets;
    }

    public void setPageScreen(boolean firstTime){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASK_SELECT_TARGET.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.selectTargetController = fxmlLoader.getController();
            this.selectTargetController.setMainController(this);
            this.selectTargetController.setAppController(this.appController);
            selectTargetController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
            if(!firstTime){
                this.targetsTableController.setSelectedTargets(this.selectedTargets);
               this.selectTargetController.getWhat_if_CB().setSelected(this.isWhatIf);
            }
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public SelectTargetController getSelectTargetController() {
        return selectTargetController;
    }

    public TargetsTableController getTargetsTableController() {
        return targetsTableController;
    }

    public void setSelectTaskScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASK_SELECT_TASK.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.page_SP ,fxmlLoader.load(url.openStream()));
            this.selectTaskScreenController = fxmlLoader.getController();
            this.selectTaskScreenController.setMainController(this);
            this.selectTaskScreenController.setAppController(this.appController);
            selectTaskScreenController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public void setWhatIfTableDirection(String direction){
        this.whatIfDirection = direction;
    }

    public String getWhatIfDirection() {
        return whatIfDirection;
    }

    public void setRunTaskScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TASK_RUN_TASK.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.main_screen ,fxmlLoader.load(url.openStream()));
            this.runTaskController = fxmlLoader.getController();
            this.runTaskController.setMainController(this);
            this.runTaskController.setAppController(this.appController);
            runTaskController.getFall_screen_SP().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.99));
        } catch (Exception e){
            System.out.println("Error in setPageScreen() - showPathController");
        }
    }

    public void setIsOneTargetSelectFromTable(boolean select){
        this.selectTargetController.oneTargetSelectProperty().set(select);
    }

    public void setTableScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(AppFxmlPath.TARGET_TABLE.toString());
            fxmlLoader.setLocation(url);
            this.appController.setArea(this.table_SP ,fxmlLoader.load(url.openStream()));
            this.targetsTableController = fxmlLoader.getController();
            this.targetsTableController.setAppController(this.appController);
            this.targetsTableController.setTasksController(this);
            this.targetsTableController.setMaxSelect(this.targetsTableController.getCountTargets());
            this.targetsTableController.getTable().prefHeightProperty().bind(this.data_area.heightProperty().multiply(0.925));
        }catch (Exception e){

        }
    }

    public void setTableButtonDisable(){
        this.deselect_all_btn.setDisable(true);
        this.select_all_btn.setDisable(true);
    }

    public void setTableButtonEnable(){
        this.deselect_all_btn.setDisable(false);
        this.select_all_btn.setDisable(false);
    }

}
