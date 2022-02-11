package components.generalComponents.graphsTable;

import components.appScreen.AppController;
import components.generalComponents.targetsTable.TargetFX;
import components.generalComponents.usersTable.UserListRefresher;
import dtoObjects.GraphDTO;
import dtoObjects.UserDTO;
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

public class GraphTableController extends components.mainControllers.Controllers implements Closeable {
    private Timer timer;
    private TimerTask listRefresher;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty(true);
    private IntegerProperty totalGraphs = new SimpleIntegerProperty(0);
    private String selected;
    private BooleanProperty isSelected = new SimpleBooleanProperty(false);


    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    @FXML
    private TableView<GraphFx> table;

    @FXML
    private TableColumn<GraphFx, CheckBox> selectCol;

    @FXML
    private TableColumn<GraphFx, String> nameCol;

    @FXML
    private TableColumn<GraphFx, Integer> activeTasksCol;

    @FXML
    private TableColumn<GraphFx, Integer> totalTasksCol;

    @Override
    public void close() {
        table.getItems().clear();
        totalGraphs.set(0);
        if (listRefresher != null && timer != null) {
            listRefresher.cancel();
            timer.cancel();
        }
    }

    @FXML
    public void initialize(){
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.activeTasksCol.setCellValueFactory(new PropertyValueFactory<>("activeTasks"));
        this.totalTasksCol.setCellValueFactory(new PropertyValueFactory<>("totalTasks"));
        this.selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
    }

    public BooleanProperty autoUpdatesProperty() {
        return autoUpdate;
    }

    public TableView<GraphFx> getTable() {
        return table;
    }

    private void updateGraphList(List<GraphFx> graphFxes) {
        Platform.runLater(() -> {
            ObservableList<GraphFx> items = table.getItems();
            items.clear();
            items.addAll(graphFxes);
            setSelectOnClick(table.getItems());
            if(this.selected != null){
                for (GraphFx graphFx : table.getItems()){
                    if(graphFx.getName().equals(selected)){
                        graphFx.getSelect().setSelected(true);
                    }
                }
            }
            totalGraphs.set(graphFxes.size());
        });
    }

    private void setSelectOnClick(Collection<GraphFx> graphs){
        for (GraphFx graphFx : graphs){
            CheckBox selectCheckBox = graphFx.getSelect();
            selectCheckBox.selectedProperty().addListener((a,b,c)->{
                if(selectCheckBox.isSelected()){
                    this.selected = graphFx.getName();
                    this.isSelected.set(true);
                    this.appController.setGraphName(graphFx.getName());
                }else{
                    this.selected = null;
                    this.isSelected.set(false);
                    this.appController.setLoadFile(false);
                }
            });
            selectCheckBox.disableProperty().bind(isSelected.and(selectCheckBox.selectedProperty().not()) );
        }
    }

    public void startListRefresher() {
        listRefresher = new GraphListRefresher(
                autoUpdate,
                this::updateGraphList);
        timer = new Timer();
        timer.schedule(listRefresher, 1000, 1000);
    }

}
