package dtoObjects;

import Enums.TargetPosition;
import graph.SerialSet;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.HashSet;

public class SerialSetFXDTO {
    private String name;
    private String list;
    private HashSet<String> set;

    public SerialSetFXDTO(SerialSetFXDTO other){
        this.name = other.name;
        //this.list = other.list;
        this.set = new HashSet<>(other.set);
    }

    public String getName() {
        return name;
    }

    public String getList() {
        return list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(String list) {
        this.list = list;
    }

    /* ******************** */
    public SerialSetFXDTO(String name, String list) {
        this.name = name;
        this.list = list;
    }

    public SerialSetFXDTO(SerialSet serialSet){
        this.name = serialSet.getName();
        this.set = new HashSet<>(serialSet.getAllSet().keySet());
    }
}
