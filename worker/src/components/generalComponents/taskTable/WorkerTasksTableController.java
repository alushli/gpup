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
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WorkerTasksTableController extends components.workerMainControllers.workerControllers implements Closeable {
    private Timer timer;
    private TimerTask listRefresher;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty(true);
    private IntegerProperty totalTasks = new SimpleIntegerProperty(0);
    private String selected;
    private WorkerTaskFX workerTaskFXSelected;

    @FXML
    private TableView<WorkerTaskFX> table;

    @FXML
    private TableColumn<WorkerTaskFX, String> nameCol;

    @FXML
    private TableColumn<WorkerTaskFX, String> createdByCol;

    @FXML
    private TableColumn<WorkerTaskFX, String> graphNameCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer> numOfTargetsCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer> rootsCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer> middleCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer> leafsCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer> independentsCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer> totalPriceCol;

    @FXML
    private TableColumn<WorkerTaskFX, Integer>numOfWorkersCol;

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
    }

    public BooleanProperty autoUpdatesProperty() {
        return autoUpdate;
    }

    public TableView<WorkerTaskFX> getTable() {
        return table;
    }

    private void updateTaskList(List<WorkerTaskFX> workerTaskFXES) {
        Platform.runLater(() -> {
            ObservableList<WorkerTaskFX> items = table.getItems();
            items.clear();
            items.addAll(workerTaskFXES);
            setSelectOnClick(table.getItems());
            if(this.selected != null){
                for (WorkerTaskFX workerTaskFX : table.getItems()){
                    if(workerTaskFX.getName().equals(selected)){
                        workerTaskFX.getSelect().setSelected(true);
                    }
                }
            }
            totalTasks.set(workerTaskFXES.size());
        });
    }

    private void setSelectOnClick(Collection<WorkerTaskFX> tasks){
        for (WorkerTaskFX workerTaskFX : tasks){
            CheckBox selectCheckBox = workerTaskFX.getSelect();
            selectCheckBox.selectedProperty().addListener((a,b,c)->{
                if(selectCheckBox.isSelected()){
                    this.selected = workerTaskFX.getName();
                    this.workerTaskFXSelected = workerTaskFX;
                    this.appController.setSelectedTask(workerTaskFX.getName());
                }else{
                    this.selected = null;
                    this.workerTaskFXSelected = null;
                    this.appController.setSelectTask(false);
                }
            });
        }
    }

    public WorkerTaskFX getTaskFXSelected() {
        return workerTaskFXSelected;
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
