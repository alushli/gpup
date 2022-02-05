package components.generalComponents.targetsTable;

import dtoObjects.TargetDTO;
import dtoObjects.TargetFXDTO;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import newEnums.TargetPosition;
import target.Target;

import java.util.Set;

public class TargetFX {
    private String name;
    private String position;
    private int directDependsOn;
    private int directRequiredFor;
    private int totalDependsOn;
    private int totalRequiredFor;
    private String generalInfo;
    private CheckBox select;
    private Set<String> totalDependsOnString;
    private Set<String> totalRequiredForString;

    private BooleanProperty isSelected;

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position.toString();
    }

    public int getDirectDependsOn() {
        return directDependsOn;
    }

    public int getDirectRequiredFor() {
        return directRequiredFor;
    }

    public int getTotalDependsOn() {
        return totalDependsOn;
    }

    public int getTotalRequiredFor() {
        return totalRequiredFor;
    }

    public Set<String> getTotalDependsOnString() {
        return totalDependsOnString;
    }

    public Set<String> getTotalRequiredForString() {
        return totalRequiredForString;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }



    public TargetFX(String name, TargetPosition position, int directDependsOn,
                       int directRequiredFor, int totalDependsOn, int totalRequiredFor,
                       String generalInfo, Set<String> totalDependsOnString, Set<String> totalRequiredForString)  {
        this.name = name;
        this.position = position.toString();
        this.directDependsOn = directDependsOn;
        this.directRequiredFor = directRequiredFor;
        this.totalDependsOn = totalDependsOn;
        this.totalRequiredFor = totalRequiredFor;
        this.generalInfo = generalInfo;
        this.select = new CheckBox();
        this.totalDependsOnString = totalDependsOnString;
        this.totalRequiredForString = totalRequiredForString;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public TargetFX(TargetFX other){
        this.name = other.name;
        this.directRequiredFor = other.directRequiredFor;
        this.position = other.position;
        this.directDependsOn = other.directDependsOn;
        this.totalRequiredFor = other.totalRequiredFor;
        this.totalDependsOn = other.totalDependsOn;
        this.generalInfo = other.generalInfo;
        this.select = new CheckBox();
        this.totalDependsOnString = other.totalDependsOnString;
        this.totalRequiredForString = other.totalRequiredForString;
    }

    public TargetFX(Target other){
        this.name = other.getName();
        this.directRequiredFor = other.getRequiredForList().size();
        this.position = other.getPosition().toString();
        this.directDependsOn = other.getDependsOnList().size();

        this.totalRequiredFor = other.getAllHangingByTypeOfTargets("requiredFor").size();
        this.totalDependsOn = other.getAllHangingByTypeOfTargets("dependsOn").size();
        this.totalDependsOnString = other.getAllHangingByTypeOfTargets("dependsOn").keySet();
        this.totalRequiredForString = other.getAllHangingByTypeOfTargets("requiredFor").keySet();
        this.generalInfo = other.getGeneralInfo();
        this.select = new CheckBox();
        this.select.setSelected(true);
    }

    public TargetFX(TargetDTO other){
        this.name = other.getName();
        this.directRequiredFor = other.getRequiredForList().size();
        this.position = other.getPosition();
        this.directDependsOn = other.getDependsOnList().size();
        this.totalRequiredFor = other.getRequiredForList().size();
        this.totalDependsOn = other.getTotalDependsOn().size();
        this.totalDependsOnString = other.getTotalDependsOn();
        this.totalRequiredForString = other.getTotalRequireFor();
        this.generalInfo = other.getGeneralInfo();
        this.select = new CheckBox();
        this.select.setSelected(false);
    }
}
