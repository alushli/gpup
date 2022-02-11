package components.generalComponents.taskTargetTable;

import components.appScreen.AppController;
import components.tasks.TasksAdminController;
import components.tasks.taskManagement.TaskManagementDetailsController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

public class TaskTargetTableController extends components.mainControllers.Controllers{
    private TaskManagementDetailsController mainController;

    @FXML
    private StackPane main_screen;

    @FXML
    private TableView<TargetRunFX> table;

    @FXML
    private TableColumn<TargetRunFX, String> nameCol;

    @FXML
    private TableColumn<TargetRunFX, String> positionCol;

    @FXML
    private TableColumn<TargetRunFX, String> workerCol;

    @FXML
    private TableColumn<TargetRunFX, String> finishStatusCol;

    @FXML
    public void initialize(){
        if(this.nameCol != null)
            this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        if(this.positionCol != null)
            this.positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        if(this.workerCol != null)
            this.workerCol.setCellValueFactory(new PropertyValueFactory<>("worker"));
        if(this.finishStatusCol != null)
            this.finishStatusCol.setCellValueFactory(new PropertyValueFactory<>("finishStatus"));
    }

    public TableView<TargetRunFX> getTable() {
        return table;
    }

    public void setMainController(TaskManagementDetailsController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }
}
