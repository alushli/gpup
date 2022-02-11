package components.generalComponents.targetsTask;

import components.appScreen.AppController;
import components.task.taskManagment.WorkerTaskManagementController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Collection;

public class TargetsTaskController extends components.workerMainControllers.workerControllers{
    private WorkerTaskManagementController mainController;

    @FXML
    private StackPane main_screen;

    @FXML
    private TableView<TargetsTaskFX> table;

    @FXML
    private TableColumn<TargetsTaskFX, String> nameCol;

    @FXML
    private TableColumn<TargetsTaskFX, String> taskNameCol;

    @FXML
    private TableColumn<TargetsTaskFX, String> taskTypeCol;

    @FXML
    private TableColumn<TargetsTaskFX, String> statusCol;

    @FXML
    private TableColumn<TargetsTaskFX, Integer> priceCol;

    public TableView<TargetsTaskFX> getTable() {
        return table;
    }

    @FXML
    public void initialize() {
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.taskNameCol.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        this.taskTypeCol.setCellValueFactory(new PropertyValueFactory<>("taskType"));
        this.statusCol.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setMainController(WorkerTaskManagementController mainControllers) {
        this.mainController = mainControllers;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
