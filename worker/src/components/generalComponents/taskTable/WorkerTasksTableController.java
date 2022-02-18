package components.generalComponents.taskTable;

import components.appScreen.AppController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Closeable;
import java.util.*;

public class WorkerTasksTableController extends components.workerMainControllers.workerControllers implements Closeable {
    private Timer timer;
    private TimerTask listRefresher;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty(true);
    private IntegerProperty totalTasks = new SimpleIntegerProperty(0);
    private String selected;
    private DashboardTaskFX dashboardTaskFXSelected;
    private Set<String> allSelected = new HashSet<>();

    @FXML
    private TableView<DashboardTaskFX> table;

    @FXML
    private TableColumn<DashboardTaskFX, String> nameCol;

    @FXML
    private TableColumn<DashboardTaskFX, String> createdByCol;

    @FXML
    private TableColumn<DashboardTaskFX, String> graphNameCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer> numOfTargetsCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer> rootsCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer> middleCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer> leafsCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer> independentsCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer> totalPriceCol;

    @FXML
    private TableColumn<DashboardTaskFX, Integer>numOfWorkersCol;

    @FXML
    private TableColumn<DashboardTaskFX, CheckBox> subscribeCol;

    @Override
    public void close() {
        table.getItems().clear();
        totalTasks.set(0);
        if (listRefresher != null && timer != null) {
            listRefresher.cancel();
            timer.cancel();
        }
    }

    @FXML
    public void initialize(){
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.createdByCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        this.graphNameCol.setCellValueFactory(new PropertyValueFactory<>("graphName"));
        this.numOfTargetsCol.setCellValueFactory(new PropertyValueFactory<>("countTargets"));
        this.rootsCol.setCellValueFactory(new PropertyValueFactory<>("countRoots"));
        this.middleCol.setCellValueFactory(new PropertyValueFactory<>("countMiddles"));
        this.leafsCol.setCellValueFactory(new PropertyValueFactory<>("countLeaves"));
        this.independentsCol.setCellValueFactory(new PropertyValueFactory<>("countIndependents"));
        this.totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        this.numOfWorkersCol.setCellValueFactory(new PropertyValueFactory<>("numOfWorkers"));
        this.subscribeCol.setCellValueFactory(new PropertyValueFactory<>("select"));
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate.set(autoUpdate);
    }

    public BooleanProperty autoUpdatesProperty() {
        return autoUpdate;
    }

    public TableView<DashboardTaskFX> getTable() {
        return table;
    }

    private void updateTaskList(List<DashboardTaskFX> dashboardTaskFXES) {
        Platform.runLater(() -> {
            ObservableList<DashboardTaskFX> items = table.getItems();
            items.clear();
            items.addAll(dashboardTaskFXES);
            setSelectOnClick(table.getItems());
            if(this.allSelected != null){
                for (DashboardTaskFX dashboardTaskFX : table.getItems()){
                    if(allSelected.contains(dashboardTaskFX.getName())){
                        dashboardTaskFX.getSelect().setSelected(true);
                    }
                }
            }
            totalTasks.set(dashboardTaskFXES.size());
        });
    }

    private void setSelectOnClick(Collection<DashboardTaskFX> tasks){
        for (DashboardTaskFX dashboardTaskFX : tasks){
            CheckBox selectCheckBox = dashboardTaskFX.getSelect();
            selectCheckBox.selectedProperty().addListener((a,b,c)->{
                if(selectCheckBox.isSelected()){
                    this.appController.getWorkerEngine().subscribeTask(dashboardTaskFX.getName());
                    allSelected.add(dashboardTaskFX.getName());
                }else{
                    this.appController.getWorkerEngine().unsubscribeTask(dashboardTaskFX.getName());
                    allSelected.remove(dashboardTaskFX.getName());
                }
            });
        }
    }

    public DashboardTaskFX getTaskFXSelected() {
        return dashboardTaskFXSelected;
    }

    public void startListRefresher() {
        listRefresher = new WorkerTaskTableRefresher(
                autoUpdate,
                this::updateTaskList);
        timer = new Timer();
        timer.schedule(listRefresher, 1000, 1000);
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
