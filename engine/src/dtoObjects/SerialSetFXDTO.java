package dtoObjects;

import Enums.TargetPosition;
import javafx.scene.control.CheckBox;

public class SerialSetFXDTO {
    private String name;
    private String list;

    public SerialSetFXDTO(SerialSetFXDTO other){
        this.name = other.name;
        this.list = other.list;

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
}
