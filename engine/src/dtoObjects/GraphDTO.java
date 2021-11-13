package dtoObjects;

import Enums.TargetPosition;
import graph.Graph;
import java.util.Map;

public class GraphDTO {
    private String graphName;
    private String workingDirectory;
    private int countTargets, countRoots, countMiddles, countLeaves, countIndependents;

    /* constructor */
    public GraphDTO(Graph graph) {
        this.graphName = graph.getGraphName();
        this.workingDirectory = graph.getWorkingDirectory();
        Map<TargetPosition, Integer> targetPositionMap = graph.getGraphInfo();
        this.countRoots = targetPositionMap.get(TargetPosition.ROOT);
        this.countMiddles = targetPositionMap.get(TargetPosition.MIDDLE);
        this.countLeaves = targetPositionMap.get(TargetPosition.LEAF);
        this.countIndependents = targetPositionMap.get(TargetPosition.INDEPENDENT);
        this.countTargets = this.countRoots + this.countMiddles + this.countLeaves + this.countIndependents;
    }

    /* the function return graph name */
    public String getGraphName() {
        return graphName;
    }

    /* the function return graph working directory */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /* the function return the number of root targets */
    public int getCountRoots() {
        return countRoots;
    }

    /* the function return the number of middle targets */
    public int getCountMiddles() {
        return countMiddles;
    }

    /* the function return the number of leaf targets */
    public int getCountLeaves() {
        return countLeaves;
    }

    /* the function return the number of independent targets */
    public int getCountIndependents() {
        return countIndependents;
    }

    /* the function return the number of targets */
    public int getCountTargets() {
        return countTargets;
    }
}
