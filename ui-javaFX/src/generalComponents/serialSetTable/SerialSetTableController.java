package generalComponents.serialSetTable;

import dtoObjects.SerialSetFXDTO;
import dtoObjects.TargetFXDTO;
import generalComponents.GeneralComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;

public class SerialSetTableController extends GeneralComponent {
    public SerialSetTableController(){
        System.out.println("constructor");
    }

    @FXML
    private TableView<SerialSetFXDTO> table;

    @FXML
    private TableColumn<SerialSetFXDTO, String> nameCol;

    @FXML
    private TableColumn<SerialSetFXDTO, String> listCol;

    public TableView<SerialSetFXDTO> getTable() {
        return table;
    }

    @FXML
    public void initialize(){
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.listCol.setCellValueFactory(new PropertyValueFactory<>("list"));
        //Collection<SerialSetFXDTO> serialSets = null;
       //this.table.setItems(getSerialSets(serialSets));
        this.table.setItems(getSerialSetsDemo());
    }

    public ObservableList<SerialSetFXDTO> getSerialSets(Collection<SerialSetFXDTO> serialSets){
        ObservableList<SerialSetFXDTO> serialSetFXDTOS = FXCollections.observableArrayList();
        for (SerialSetFXDTO serialSetFXDTO : serialSets){
            serialSetFXDTOS.add(new SerialSetFXDTO(serialSetFXDTO));
        }
        return serialSetFXDTOS;
    }


    public ObservableList<SerialSetFXDTO> getSerialSetsDemo(){
        ObservableList<SerialSetFXDTO> serialSetFXDTOS = FXCollections.observableArrayList();
       for(int i=0;i<5;i++){
           serialSetFXDTOS.add(new SerialSetFXDTO("li","a,b,c"));
       }
        return serialSetFXDTOS;
    }
}
