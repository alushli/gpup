package components.generalComponents.graphsTable;

import dtoObjects.GraphDTO;
import javafx.scene.control.CheckBox;

public class GraphFx {
    private String name;
    private CheckBox select;

    public GraphFx(GraphDTO graphDTO){
        this.name = graphDTO.getName();
        this.select = new CheckBox();
    }

    public String getName() {
        return name;
    }

    public CheckBox getSelect() {
        return select;
    }


}
