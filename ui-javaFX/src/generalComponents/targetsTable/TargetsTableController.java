package generalComponents.targetsTable;


import Enums.TargetPosition;
import appScreen.AppController;
import enums.FxmlPath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.omg.CORBA.INTERNAL;

import java.util.Collection;

public class TargetsTableController {

    @FXML
    private TableView<ResTargetDTO> table;

    @FXML
    private TableColumn<ResTargetDTO, String> nameCol;

    @FXML
    private TableColumn<ResTargetDTO, Integer> positionCol;

    @FXML
    private TableColumn<ResTargetDTO, Integer> directDependsOnCol;

    @FXML
    private TableColumn<ResTargetDTO, Integer> totalDependsOnCol;

    @FXML
    private TableColumn<ResTargetDTO, Integer> directRequiredForCol;

    @FXML
    private TableColumn<ResTargetDTO, Integer> totalRequiredForCol;

    @FXML
    private TableColumn<ResTargetDTO, String> freeTextCol;

    @FXML
    private TableColumn<ResTargetDTO, Integer> serialSetsCol;

    @FXML
    private TableColumn<ResTargetDTO, CheckBox> selectCol;


    public TargetsTableController(Collection<ResTargetDTO> targets){
        table.setItems(getTargets(targets));
    }

    public ObservableList<ResTargetDTO> getTargets(Collection<ResTargetDTO> targets){
        ObservableList<ResTargetDTO> targetsObs = FXCollections.observableArrayList();
        for (ResTargetDTO resTargetDTO : targets){
            targetsObs.add(new ResTargetDTO(resTargetDTO));
        }
        return targetsObs;
    }

    public static ObservableList<ResTargetDTO> getTargets(){
        ObservableList<ResTargetDTO> targetsObs = FXCollections.observableArrayList();
        for (int i = 0; i<100 ; i++){
            targetsObs.add(new ResTargetDTO("Yarin", TargetPosition.ROOT,
                    10, 10, i, 10,
                    "bazini", 10));
        }
        return targetsObs;
    }

    @FXML
    public void initialize(){
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.directDependsOnCol.setCellValueFactory(new PropertyValueFactory<>("directDependsOn"));
        this.totalDependsOnCol.setCellValueFactory(new PropertyValueFactory<>("totalDependsOn"));
        this.directRequiredForCol.setCellValueFactory(new PropertyValueFactory<>("directRequiredFor"));
        this.totalRequiredForCol.setCellValueFactory(new PropertyValueFactory<>("totalRequiredFor"));
        this.freeTextCol.setCellValueFactory(new PropertyValueFactory<>("generalInfo"));
        this.serialSetsCol.setCellValueFactory(new PropertyValueFactory<>("serialSets"));
        this.positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        this.selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
        this.selectCol.setSortable(false);
        this.table.setItems(getTargets());
    }

    public TargetsTableController(){

    }

}
