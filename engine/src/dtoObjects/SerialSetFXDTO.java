package dtoObjects;

import Enums.TargetPosition;
import graph.SerialSet;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.HashSet;

public class SerialSetFXDTO {
    private String name;
    private HashSet<String> set;

    public SerialSetFXDTO(SerialSetFXDTO other){
        this.name = other.name;
        this.set = new HashSet<>(other.set);
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getSet() {
        return set;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSet(HashSet<String> set) {
        this.set = set;
    }

    public SerialSetFXDTO(SerialSet serialSet){
        this.name = serialSet.getName();
        this.set = new HashSet<>(serialSet.getAllSet().keySet());
    }
}
