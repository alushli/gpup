package components.generalComponents.targetsTable;

import components.generalComponents.GeneralComponent;
import components.tasks.CreateNewTasksController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class TargetsTableController extends GeneralComponent {
    private int maxSelect;
    private ArrayList<TargetFX> curSelected;
    private IntegerProperty selectedCounter;
    private BooleanProperty isMaxSelected;
    private CreateNewTasksController createNewTasksController;

    @FXML
    private StackPane main_screen;

    @FXML
    private TableView<TargetFX> table;

    @FXML
    private TableColumn<TargetFX, String> nameCol;

    @FXML
    private TableColumn<TargetFX, Integer> positionCol;

    @FXML
    private TableColumn<TargetFX, Integer> directDependsOnCol;

    @FXML
    private TableColumn<TargetFX, Integer> totalDependsOnCol;

    @FXML
    private TableColumn<TargetFX, Integer> directRequiredForCol;

    @FXML
    private TableColumn<TargetFX, Integer> totalRequiredForCol;

    @FXML
    private TableColumn<TargetFX, String> freeTextCol;

    @FXML
    private TableColumn<TargetFX, CheckBox> selectCol;

    public TableView<TargetFX> getTable() {
        return table;
    }

    public void setTasksController(CreateNewTasksController createNewTasksController) {
        this.createNewTasksController = createNewTasksController;
    }

    public TargetsTableController(Collection<TargetFX> targets){
        table.setItems(getTargets(targets));
    }

    public ObservableList<TargetFX> getTargets(Collection<TargetFX> targets){
        ObservableList<TargetFX> targetsObs = FXCollections.observableArrayList();
        for (TargetFX resTargetDTO : targets){
            targetsObs.add(resTargetDTO);
        }
        return targetsObs;
    }

    public int getMaxSelect() {
        return maxSelect;
    }

    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
        setSelectOnClick(this.table.getItems());
    }

    public ArrayList<TargetFX> getCurSelected() {
        return curSelected;
    }

    public int getSelectedCounter() {
        return selectedCounter.get();
    }

    public IntegerProperty selectedCounterProperty() {
        return selectedCounter;
    }

    @FXML
    public void initialize(){
        this.curSelected = new ArrayList<>();
        this.isMaxSelected = new SimpleBooleanProperty();
        this.selectedCounter = new SimpleIntegerProperty();
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.directDependsOnCol.setCellValueFactory(new PropertyValueFactory<>("directDependsOn"));
        this.totalDependsOnCol.setCellValueFactory(new PropertyValueFactory<>("totalDependsOn"));
        this.directRequiredForCol.setCellValueFactory(new PropertyValueFactory<>("directRequiredFor"));
        this.totalRequiredForCol.setCellValueFactory(new PropertyValueFactory<>("totalRequiredFor"));
        this.freeTextCol.setCellValueFactory(new PropertyValueFactory<>("generalInfo"));
        this.positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        this.selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
        this.selectCol.setSortable(false);

        Collection<TargetFX> targets = this.getAppController().getAllTargets();

        this.table.setItems(getTargets(targets));
    }


    public void setSelectOnClick(Collection<TargetFX> targets){
        for (TargetFX targetFX : targets){
            CheckBox selectCheckBox = targetFX.getSelect();
            selectCheckBox.selectedProperty().addListener((a,b,c)->{
                if(selectCheckBox.isSelected()){
                    this.curSelected.add(targetFX);
                    this.selectedCounter.setValue(this.curSelected.size());
                    if(this.selectedCounter.getValue() == this.maxSelect){
                        this.isMaxSelected.set(true);
                    }
                    if(this.createNewTasksController != null) {
                        this.createNewTasksController.getSelectedTargets().add(targetFX);
                        updateSelectedForTask();
                    }
                }else{
                    if(this.createNewTasksController != null) {
                        this.createNewTasksController.getSelectedTargets().remove(targetFX);
                        updateSelectedForTask();
                    }
                    this.curSelected.remove(targetFX);
                    this.selectedCounter.setValue(this.curSelected.size());
                    if(this.selectedCounter.getValue() < this.maxSelect) {
                        this.isMaxSelected.set(false);
                    }
                }

            });
            selectCheckBox.disableProperty().bind(isMaxSelected.and(selectCheckBox.selectedProperty().not()) );
        }
    }

    private void updateSelectedForTask(){
        if(getCountSelectedTargets() >= 1)
            this.createNewTasksController.setIsOneTargetSelectFromTable(true);
        else
            this.createNewTasksController.setIsOneTargetSelectFromTable(false);
    }


    public void deselectAll(){
        for(TargetFX targetFX: this.table.getItems()){
            if(targetFX.getSelect().isSelected()){
                targetFX.getSelect().setSelected(false);
            }
        }
    }

    private int getCountSelectedTargets(){
        int count =0;
        for(TargetFX targetFX: this.table.getItems()){
            if(targetFX.getSelect().isSelected()){
               count++;
            }
        }
        return count;
    }

    public void SelectAll(){
        for(TargetFX targetFX: this.table.getItems()){
            if(!targetFX.getSelect().isSelected()){
                targetFX.getSelect().setSelected(true);
            }
        }
    }


    public TargetsTableController(){

    }

    public void setSelectedTargets(ArrayList<TargetFX> arrayList){
        for(TargetFX targetFXDTO: arrayList)
            targetFXDTO.getSelect().setSelected(true);
    }

    public int getCountTargets(){
        return this.table.getItems().size();
    }

    public void setSelectDisable(){
        for(TargetFX targetFXDTO: this.table.getItems()){
            targetFXDTO.getSelect().disableProperty().unbind();
            targetFXDTO.getSelect().setDisable(true);
        }
    }

    public void setSelectActive(){
        for(TargetFX targetFXDTO: this.table.getItems()){
            targetFXDTO.getSelect().disableProperty().unbind();
            targetFXDTO.getSelect().setDisable(false);
        }
    }

}
