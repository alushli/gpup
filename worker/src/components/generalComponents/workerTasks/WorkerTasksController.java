package components.generalComponents.workerTasks;

import components.appScreen.AppController;
import components.task.taskManagment.WorkerTaskManagementController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WorkerTasksController extends components.workerMainControllers.workerControllers{
    private WorkerTaskManagementController mainController;
    private Set<String> paused = new HashSet<>();
    
    @FXML
    private StackPane main_screen;

    @FXML
    private TableView<WorkerTasksFX> table;

    @FXML
    private TableColumn<WorkerTasksFX, String> nameCol;

    @FXML
    private TableColumn<WorkerTasksFX, Integer> workersCol;

    @FXML
    private TableColumn<WorkerTasksFX, Integer> targetsCol;

    @FXML
    private TableColumn<WorkerTasksFX, Integer> doneTargetsCol;

    @FXML
    private TableColumn<WorkerTasksFX, Integer> targetByMeCol;

    @FXML
    private TableColumn<WorkerTasksFX, Integer> creditCol;

    @FXML
    private TableColumn<WorkerTasksFX, CheckBox> activeCol;

    @FXML
    private TableColumn<WorkerTasksFX, String> statusCol;

    public TableView<WorkerTasksFX> getTable() {
        return table;
    }

    @FXML
    public void initialize() {
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.workersCol.setCellValueFactory(new PropertyValueFactory<>("workers"));
        this.targetsCol.setCellValueFactory(new PropertyValueFactory<>("totalTargets"));
        this.doneTargetsCol.setCellValueFactory(new PropertyValueFactory<>("doneTargets"));
        this.targetByMeCol.setCellValueFactory(new PropertyValueFactory<>("byMe"));
        this.creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        this.activeCol.setCellValueFactory(new PropertyValueFactory<>("activeSelect"));
        this.activeCol.setSortable(false);
        this.statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void updateTable(Collection<WorkerTasksFX> tasks){
        Platform.runLater(() -> {
            ObservableList<WorkerTasksFX> items = table.getItems();
            items.clear();
            items.addAll(tasks);
            setSelectOnClick(table.getItems());
            if(paused != null){
                for (WorkerTasksFX workerTasksFX : table.getItems()){
                    if(paused.contains(workerTasksFX.getName())){
                        workerTasksFX.getActiveSelect().setSelected(true);
                    }
                }
            }
        });
    }

    private void setSelectOnClick(Collection<WorkerTasksFX> tasks){
       for (WorkerTasksFX workerTasksFX : tasks){
            CheckBox pause = workerTasksFX.getActiveSelect();
           pause.selectedProperty().addListener((a,b,c)->{
                if(pause.isSelected()){
                    if(!paused.contains(workerTasksFX.getName())){
                        this.appController.getWorkerEngine().pauseTask(workerTasksFX.getName(), "pause");
                        this.paused.add(workerTasksFX.getName());
                    }

                }else{
                    if(paused.contains(workerTasksFX.getName())){
                        this.appController.getWorkerEngine().pauseTask(workerTasksFX.getName(), "resume");
                        this.paused.remove(workerTasksFX.getName());
                    }
                }
            });
        }
    }



    public void setMainController(WorkerTaskManagementController mainControllers) {
        this.mainController = mainControllers;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }



}
