package components.generalComponents.tasksTable;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TasksTableController {
    @FXML
    private TableView<TaskFX> table;
    @FXML
    private TableColumn<TaskFX, CheckBox> selectCol;

    @FXML
    private TableColumn<TaskFX, String> nameCol;

    @FXML
    private TableColumn<TaskFX, String> createdByCol;

    @FXML
    private TableColumn<TaskFX, String> graphNameCol;

    @FXML
    private TableColumn<TaskFX, Integer> numOfTargetsCol;

    @FXML
    private TableColumn<TaskFX, Integer> rootsCol;

    @FXML
    private TableColumn<TaskFX, Integer> middleCol;

    @FXML
    private TableColumn<TaskFX, Integer> leafsCol;

    @FXML
    private TableColumn<TaskFX, Integer> independentsCol;

    @FXML
    private TableColumn<TaskFX, Integer> totalPriceCol;

    @FXML
    private TableColumn<TaskFX, Integer>numOfWorkersCol;





}
