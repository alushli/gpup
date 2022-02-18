package components.generalComponents.graphsTable;

import dtoObjects.GraphDTO;
import javafx.scene.control.CheckBox;

public class GraphFx {
    private String name;
    private CheckBox select;
    private boolean canSimulation;
    private boolean canCompilation;


    public GraphFx(GraphDTO graphDTO){
        this.name = graphDTO.getName();
        this.select = new CheckBox();
        this.canCompilation = graphDTO.isCanCompilation();
        this.canSimulation = graphDTO.isCanSimulation();
    }

    public String getName() {
        return name;
    }

    public CheckBox getSelect() {
        return select;
    }

    public boolean isCanSimulation() {
        return canSimulation;
    }

    public boolean isCanCompilation() {
        return canCompilation;
    }
}
