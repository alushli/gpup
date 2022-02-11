package components.generalComponents.usersTable;

import components.appScreen.AppController;
import components.generalComponents.targetsTable.TargetFX;
import dtoObjects.UserDTO;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Closeable;
import java.util.*;

public class UserTableController extends components.mainControllers.Controllers implements Closeable{
    private Timer timer;
    private TimerTask listRefresher;
    private  BooleanProperty autoUpdate = new SimpleBooleanProperty(true);
    private  IntegerProperty totalUsers = new SimpleIntegerProperty(0);

    @FXML
    private TableView<UserDTO> table;

    @FXML
    private TableColumn<UserDTO,String> nameCol;

    @FXML
    private TableColumn<UserDTO,String> roleCol;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public TableView<UserDTO> getTable() {
        return table;
    }

    @FXML
    public void initialize(){
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    @Override
    public void close() {
        table.getItems().clear();
        totalUsers.set(0);
        if (listRefresher != null && timer != null) {
            listRefresher.cancel();
            timer.cancel();
        }
    }

    public BooleanProperty autoUpdatesProperty() {
        return autoUpdate;
    }

    private void updateUsersList(List<UserDTO> users) {
        Platform.runLater(() -> {
            ObservableList<UserDTO> items = table.getItems();
            items.clear();
            items.addAll(users);
            totalUsers.set(users.size());
        });
    }

    public void startListRefresher() {
        listRefresher = new UserListRefresher(
                autoUpdate,
                this::updateUsersList);
        timer = new Timer();
        timer.schedule(listRefresher, 1000, 1000);
    }
}
