package components.generalComponents.graphsTable;

import dtoObjects.GraphDTO;
import javafx.scene.control.CheckBox;

public class GraphFx {
    private String name;
    private int activeTasks;
    private int totalTasks;
    private CheckBox select;

    public GraphFx(GraphDTO graphDTO){
        this.name = graphDTO.getName();
        this.activeTasks = graphDTO.getActiveTasks();
        this.totalTasks = graphDTO.getTotalTasks();
        this.select = new CheckBox();
    }

    public String getName() {
        return name;
    }

    public int getActiveTasks() {
        return activeTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public CheckBox getSelect() {
        return select;
    }


}
