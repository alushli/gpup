package graph;

import sun.lwawt.macosx.CSystemTray;
import target.Target;

import java.lang.reflect.Array;
import java.util.*;

public class Graph {
    private Map<Target, List<Target>> map;

    public Graph(){
        this.map = new HashMap<>();
    }


    public void addToGr(Target target){
        this.map.put(target, target.getDependsOnList());
    }

    public boolean isEmpty(){
        return this.map.isEmpty();
    }

    public List<Target> getRunnableTargets(){
        List<Target> runableTargesList = new ArrayList<>();
        for(Map.Entry<Target, List<Target>> entry: this.map.entrySet()){
            if(entry.getValue().size() == 0){
                runableTargesList.add(entry.getKey());
            }
        }
        return runableTargesList;
    }

    public void removeTarget(Target target){
        this.map.remove(target);
    }

    public String getGraphInfo(){
        int countRoots = 0, countMiddle = 0, countLeaf = 0, countIndependents = 0;
        String info = new String();
        info += "There are " + map.size() +" targets on the graph. ";
        for(Target target: map.keySet()){
            switch (target.getPosition()){
                case ROOT:
                    countRoots++;
                    break;
                case MIDDLE:
                        countMiddle++;
                        break;
                case LEAF:
                    countLeaf++;
                    break;
                case INDEPENDENT:
                    countIndependents++;
                    break;
            }
        }
        info += "There are " + countRoots + " Roots, " + countMiddle + " Middles, " + countLeaf + " Leafs, " + countIndependents
                + " Indpendents targets on the graph.";
        return info;
    }

    public List<List<Target>> findAllPaths (Target src, Target dec){
        List<List<Target>> allPaths = new ArrayList<>();
        Set<Target> visited = new LinkedHashSet<>();
        allPaths.add(new ArrayList<>());
        findAllPathsRec(src, dec, visited,allPaths);
        allPaths.remove(allPaths.size()-1);
        return allPaths;
    }

    private void findAllPathsRec (Target src, Target dec, Set<Target> visited, List<List<Target>> allPaths){
        if (src.equals(dec)) {
            update(visited, allPaths.get(allPaths.size()-1));
            allPaths.get(allPaths.size()-1).add(src);
            allPaths.add(new ArrayList<>());
            return;
        }
        visited.add(src);
        for(Target target: src.getDependsOnList()){
            if(!visited.contains(target)){
                findAllPathsRec(target, dec, visited, allPaths);
            }
        }
        visited.remove(src);
    }

    private void update( Set<Target> visited, List<Target> path){
        for (Target target:visited)
            path.add(target);
    }

    public LinkedHashSet<Target> findCircle(Target target){
        LinkedHashSet<Target> circle = new LinkedHashSet<>();
        for (Target target1: target.getDependsOnList()){
            findCircleRec(target1, target, circle);
            if(circle.contains(target)){
                return circle;
            }else{
                circle.clear();
            }
        }
        return circle;
    }

    private void findCircleRec(Target src, Target dst, LinkedHashSet<Target> path){
        if(src.equals(dst)){
            path.add(src);
            return;
        }
            path.add(src);
            for (Target target : src.getDependsOnList()) {
                if (!path.contains(target)) {
                    findCircleRec(target, dst, path);
                    if(!path.contains(dst)){
                        path.remove(target);
                    }else{
                        return;
                    }
                }
            }
        }

    // handle sort graph - doesnt use
    public void printOrderMap(Map<Target, List<Target>> map){
        for (Map.Entry<Target, List<Target>> entry : map.entrySet()){
            System.out.println(entry.getKey().toString());
        }
    }

    public void sortMap(){
        List<Target> roots = new ArrayList<>();
        List<Target> independents = new ArrayList<>();

        Map<Target, List<Target>> orderMap = new LinkedHashMap<>();
        for (Map.Entry<Target, List<Target>> entry : this.map.entrySet()) {
            if(entry.getKey().getRequiredForList().size() == 0){
                if(entry.getValue().size() == 0){
                    independents.add(entry.getKey());
                }else{
                    roots.add(entry.getKey());
                }
            }
        }
        insertListToOrderMap(orderMap, independents);
        sortMapUtil(orderMap, roots);
        printOrderMap(orderMap);

    }

    private void insertListToOrderMap(Map<Target, List<Target>> orderMap, List<Target> listToAdd){
        for (Target target : listToAdd){
            List<Target> value = new ArrayList<>();
            orderMap.put(target,value);
        }
    }

    private void sortMapUtil(Map<Target, List<Target>> orderMap,  List<Target> roots){
        for(Target target : roots){
            sortMapUtilRec(orderMap,target);
        }
    }

    private void sortMapUtilRec(Map<Target, List<Target>> orderMap, Target root){
        List<Target> dependsOnList = root.getDependsOnList();
        for (Target target : dependsOnList){
            sortMapUtilRec(orderMap, target);
        }

        orderMap.put(root,new ArrayList<>(root.getDependsOnList()));
    }


}
