package components.generalComponents.workerTasks;

import components.appScreen.AppController;
import components.task.taskManagment.WorkerTaskManagementController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

public class WorkerTasksController extends components.workerMainControllers.workerControllers{
    private WorkerTaskManagementController mainController;
    
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
    private TableColumn<WorkerTasksFX, CheckBox> unsubscribeCol;

    public TableView<WorkerTasksFX> getTable() {
        return table;
    }

    @FXML
    public void initialize() {
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.workersCol.setCellValueFactory(new PropertyValueFactory<>("workers"));
        this.targetsCol.setCellValueFactory(new PropertyValueFactory<>("targets"));
        this.doneTargetsCol.setCellValueFactory(new PropertyValueFactory<>("doneTargets"));
        this.targetByMeCol.setCellValueFactory(new PropertyValueFactory<>("byMe"));
        this.creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        this.activeCol.setCellValueFactory(new PropertyValueFactory<>("activeSelect"));
        this.activeCol.setSortable(false);
        this.unsubscribeCol.setCellValueFactory(new PropertyValueFactory<>("unsubscribeSelect"));
        this.unsubscribeCol.setSortable(false);
    }

    public void setMainController(WorkerTaskManagementController mainControllers) {
        this.mainController = mainControllers;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
