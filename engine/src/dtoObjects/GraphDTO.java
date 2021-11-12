package dtoObjects;

import graph.Graph;
import target.Target;
import java.util.List;
import java.util.Map;

public class GraphDTO {
    private String graphName;
    private String workingDirectory;
    private Map<TargetDTO, List<TargetDTO>> map;

    /* constructor */
    public GraphDTO(Graph graph){
        this.graphName = graph.getGraphName();
        this.workingDirectory = graph.getWorkingDirectory();
        duplicateMap(graph.getGraphMap());
    }

    /* the function duplicate the graph map */
    private void duplicateMap(Map<Target, List<Target>> other){
        for(Map.Entry<Target, List<Target>> entry : other.entrySet()){
            TargetDTO target = new TargetDTO(entry.getKey());
            this.map.put(target, target.getDependsOnList());
        }
    }

    /* the function return graph name */
    public String getGraphName() {
        return graphName;
    }

    /* the function return graph working directory */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /* the function return graph map */
    public Map<TargetDTO, List<TargetDTO>> getMap() {
        return map;
    }
}
