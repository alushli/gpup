package dtoObjects;

import graph.Graph;
import newEnums.TargetPosition;

import java.util.HashMap;
import java.util.Map;

public class GeneralGraphInfoDTO {
    private String name;
    private int countTargets;
    private int countLeaves;
    private int countIndependents;
    private int countRoots;
    private int countMiddles;


    public GeneralGraphInfoDTO(Graph graph){
        this.name = graph.getGraphName();
        Map<TargetPosition, Integer> targetPositionMap = graph.getGraphInfo();
        this.countRoots = targetPositionMap.get(TargetPosition.ROOT);
        this.countMiddles = targetPositionMap.get(TargetPosition.MIDDLE);
        this.countLeaves = targetPositionMap.get(TargetPosition.LEAF);
        this.countIndependents = targetPositionMap.get(TargetPosition.INDEPENDENT);
        this.countTargets = this.countRoots + this.countMiddles + this.countLeaves + this.countIndependents;
    }

    public String getName() {
        return name;
    }

    public int getCountTargets() {
        return countTargets;
    }

    public int getCountLeaves() {
        return countLeaves;
    }

    public int getCountIndependents() {
        return countIndependents;
    }

    public int getCountRoots() {
        return countRoots;
    }

    public int getCountMiddles() {
        return countMiddles;
    }
}
