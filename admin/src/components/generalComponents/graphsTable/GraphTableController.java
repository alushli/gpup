package components.generalComponents.graphsTable;

import dtoObjects.GraphDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GraphTableController {
    @FXML
    private TableView<GraphDTO> table;

    @FXML
    private TableColumn<GraphDTO, ?> selectCol;

    @FXML
    private TableColumn<GraphDTO, String> nameCol;

    @FXML
    private TableColumn<GraphDTO, String> activeTasksCol;

    @FXML
    private TableColumn<GraphDTO, String> totalTasksCol;



}
