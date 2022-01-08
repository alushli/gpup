package generalComponents.serialSetTable;

import dtoObjects.SerialSetFXDTO;
import enums.StyleSheetsPath;
import generalComponents.GeneralComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.Collection;

public class SerialSetTableController extends GeneralComponent {
    private StringProperty skin;

    @FXML
    private StackPane main_screen;

    public SerialSetTableController(){
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

    public StringProperty skinProperty() {
        return skin;
    }

    @FXML
    public void initialize(){
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.listCol.setCellValueFactory(new PropertyValueFactory<>("set"));
        this.table.setItems(getSerialSets(this.getAppController().getGraphSerialSet()));
        this.skin = new SimpleStringProperty("Light");
        this.skin.addListener((a, b, c)->{
            if(this.skin.getValue().equals("Light")){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.SERIAL_SET_TABLE_DARK.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.SERIAL_SET_TABLE_PRINCESS.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.SERIAL_SET_TABLE_LIGHT.toString());
            }else if (this.skin.getValue().equals("Dark")){
                this.main_screen.getStylesheets().remove(StyleSheetsPath.SERIAL_SET_TABLE_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.SERIAL_SET_TABLE_PRINCESS.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.SERIAL_SET_TABLE_DARK.toString());
            } else{
                this.main_screen.getStylesheets().remove(StyleSheetsPath.SERIAL_SET_TABLE_LIGHT.toString());
                this.main_screen.getStylesheets().remove(StyleSheetsPath.SERIAL_SET_TABLE_DARK.toString());
                this.main_screen.getStylesheets().add(StyleSheetsPath.SERIAL_SET_TABLE_PRINCESS.toString());
            }
        });
    }

    public ObservableList<SerialSetFXDTO> getSerialSets(Collection<SerialSetFXDTO> serialSets){
        ObservableList<SerialSetFXDTO> serialSetFXDTOS = FXCollections.observableArrayList();
        for (SerialSetFXDTO serialSetFXDTO : serialSets){
            serialSetFXDTOS.add(new SerialSetFXDTO(serialSetFXDTO));
        }
        return serialSetFXDTOS;
    }

}
