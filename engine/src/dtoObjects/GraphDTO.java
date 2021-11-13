package dtoObjects;

import graph.Graph;
import target.Target;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphDTO {
    private String graphName;
    private String workingDirectory;
    private int countRoots, countMiddles, countLeaves, countIndependents;
//****************
    private Map<String, Set<String>> map;

    /* constructor */
    public GraphDTO(Graph graph){
        this.graphName = graph.getGraphName();
        this.workingDirectory = graph.getWorkingDirectory();
        duplicateMap(graph.getGraphMap());
    }

    /* the function duplicate the graph map */
    private void duplicateMap(Map<Target, Set<Target>> other){
        for(Map.Entry<Target, Set<Target>> entry : other.entrySet()){
            TargetDTO target = new TargetDTO(entry.getKey());
            this.map.put(entry.getKey().getName(), target.getDependsOnList());
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


}
