package dtoObjects;

import Enums.TargetPosition;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import target.Target;


import java.awt.*;
import java.util.Observable;
import java.util.Set;

public class TargetFXDTO {
    private String name;
    private TargetPosition position;
    private int directDependsOn;
    private int directRequiredFor;
    private int totalDependsOn;
    private int totalRequiredFor;
    private String generalInfo;
    private int serialSets;
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

    public int getSerialSets() {
        return serialSets;
    }


    public TargetFXDTO(String name, TargetPosition position, int directDependsOn,
                       int directRequiredFor, int totalDependsOn, int totalRequiredFor,
                       String generalInfo, int serialSets, Set<String> totalDependsOnString, Set<String> totalRequiredForString) {
        this.name = name;
        this.position = position;
        this.directDependsOn = directDependsOn;
        this.directRequiredFor = directRequiredFor;
        this.totalDependsOn = totalDependsOn;
        this.totalRequiredFor = totalRequiredFor;
        this.generalInfo = generalInfo;
        this.serialSets = serialSets;
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

    public TargetFXDTO(TargetFXDTO other){
        this.name = other.name;
        this.directRequiredFor = other.directRequiredFor;
        this.position = other.position;
        this.directDependsOn = other.directDependsOn;
        this.totalRequiredFor = other.totalRequiredFor;
        this.totalDependsOn = other.totalDependsOn;
        this.serialSets = other.serialSets;
        this.generalInfo = other.generalInfo;
        this.select = new CheckBox();
        this.totalDependsOnString = other.totalDependsOnString;
        this.totalRequiredForString = other.totalRequiredForString;
    }

    public TargetFXDTO(Target other){
        this.name = other.getName();
        this.directRequiredFor = other.getRequiredForList().size();
        this.position = other.getPosition();
        this.directDependsOn = other.getDependsOnList().size();

        this.totalRequiredFor = other.getAllHangingByTypeOfTargets("requiredFor").size();
        this.totalDependsOn = other.getAllHangingByTypeOfTargets("dependsOn").size();
        this.serialSets = other.getSerialSetMap().size();
        this.totalDependsOnString = other.getAllHangingByTypeOfTargets("dependsOn").keySet();
        this.totalRequiredForString = other.getAllHangingByTypeOfTargets("requiredFor").keySet();
        this.generalInfo = other.getGeneralInfo();
        this.select = new CheckBox();
        this.select.setSelected(true);
    }

}
