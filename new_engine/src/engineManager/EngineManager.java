package engineManager;

import XmlUtils.XmlUtils;
import dtoObjects.TargetDTO;
import newExceptions.XmlException;
import graph.Graph;
import scema.generated.*;
import target.Target;

import java.io.InputStream;
import java.util.*;

public class EngineManager {
    private Map<String, Graph> graphMap = new HashMap<>();

    public synchronized void addGraph(Graph graph) {
        this.graphMap.put(graph.getGraphName(), graph);
    }

    public synchronized Graph getGraph(String name){
        if(graphMap.containsKey(name))
            return graphMap.get(name);
        else
            return null;
    }

    /* the function load the graph */
    public void load(InputStream in, Set<String> errors) {
        try{
            List<Graph> graphsList = loadHelper(in,errors);
            Graph graphOrigin = graphsList.get(0);
            if(errors.isEmpty()){
                this.graphMap.put(graphOrigin.getGraphName(), graphOrigin);
            }
        }catch (XmlException e){
            return;
        }

    }

    private List<Graph> loadHelper(InputStream filePath, Set<String> errors) throws XmlException {
        List<Graph> graphsList = new ArrayList<>();
        GPUPDescriptor root = (GPUPDescriptor) XmlUtils.readFromXml(filePath, new GPUPDescriptor());
        if (root.getGPUPTargets() != null && root.getGPUPConfiguration() != null) {
            List<Map<String, Target>> mapsList = getMapsOfTargets(root.getGPUPTargets(), errors);
            int simulationPrice = -1, compilationPrice = -1;
            if(root.getGPUPConfiguration().getGPUPPricing().getGPUPTask() != null){
                for(GPUPConfiguration.GPUPPricing.GPUPTask gpupTask : root.getGPUPConfiguration().getGPUPPricing().getGPUPTask()){
                    if(gpupTask.getName().equals("Simulation")){
                        simulationPrice = gpupTask.getPricePerTarget();
                    }else{
                        compilationPrice = gpupTask.getPricePerTarget();
                    }
                }
            }
            if(this.graphMap.containsKey(root.getGPUPConfiguration().getGPUPGraphName())){
                errors.add("Graph name already exist");
            }
            Graph graphOrigin = new Graph(root.getGPUPConfiguration().getGPUPGraphName(),simulationPrice,compilationPrice);
            Graph graphIncremental = new Graph(root.getGPUPConfiguration().getGPUPGraphName(),simulationPrice,compilationPrice);
            if (errors.isEmpty()) {
                addToTargetList(mapsList.get(0), root, graphOrigin, true, errors);
            }
            if (!errors.isEmpty()) {
                throw new XmlException(errors.toString());
            }
            graphsList.add(graphOrigin);
            graphsList.add(graphIncremental);
            return graphsList;
        } else {
            throw new XmlException("The file doesn't fit the schema.");
        }
    }

    /* the function return map of targets (0 - origin, 1- incremental) */
    private List<Map<String, Target>> getMapsOfTargets(GPUPTargets targets, Set<String> errors) {
        GPUPTarget targetError = null;
        List<Map<String, Target>> mapsList = new ArrayList<>();
        mapsList.add(new HashMap<>());
        mapsList.add(new HashMap<>());
        for (GPUPTarget gpupTarget : targets.getGPUPTarget()) {
            if (!mapsList.get(0).keySet().contains(gpupTarget.getName())) {
                addToMap(gpupTarget, mapsList.get(0));
            } else{
                targetError = gpupTarget;
            }

            if (targetError != null) {
            String error = new String();

            error += "Target:" + gpupTarget.getName();
            error += " appears more than one";
            errors.add(error);
        }
            targetError = null;
        }
        return mapsList;
    }

    /* the function add the target to map */
    private void addToMap(GPUPTarget gpupTarget, Map<String, Target> map){
        Target target = new Target(gpupTarget.getName());
        map.put(gpupTarget.getName(), target);
        if(gpupTarget.getGPUPUserData() != null) {
            target.updateGeneralInfo(gpupTarget.getGPUPUserData());
        }
    }

    /* the function add targets to target list */
    private void addToTargetList(Map<String, Target> map, GPUPDescriptor root, Graph graph, boolean isOrigin, Set<String> errors) {
        for(GPUPTarget gpupTarget : root.getGPUPTargets().getGPUPTarget()){
            if(!isOrigin){
                updateTargetLists(gpupTarget, map, errors);
            }
        }
        if(isOrigin){
            for (GPUPTarget gpupTarget : root.getGPUPTargets().getGPUPTarget()){
                graph.addToGr(map.get(gpupTarget.getName()));
            }
        }
    }

    /* the function update target depends on and required for lists */
    private void updateTargetLists(GPUPTarget gpupTarget, Map<String, Target> map, Set<String> errors){
        Target target = map.get(gpupTarget.getName());
        if(gpupTarget.getGPUPTargetDependencies() != null){
            for (GPUPTargetDependencies.GPUGDependency dependency : gpupTarget.getGPUPTargetDependencies().getGPUGDependency()) {
                String name = dependency.getValue();
                if (map.keySet().contains(name.trim())) {
                    addToTargetListByType(target, dependency, map.get(name.trim()), errors);
                } else {
                    String newError = target.getName() + " " + dependency.getType() + " " + dependency.getValue()
                            + " but " + dependency.getValue() + " not exist";
                    errors.add(newError);
                }
            }
        }
    }

    /* the function add target to lists by type */
    private void addToTargetListByType(Target target, GPUPTargetDependencies.GPUGDependency dependency, Target targetToAdd,
                                       Set<String> errors){
        boolean error = false;
        if(target.equals(targetToAdd)){
            errors.add(target.getName() + " cant be depends on itself");
        }else {
            if (dependency.getType().equals("dependsOn")) {
                if (!targetToAdd.isInDependsOnList(target)) {
                    target.addToDependsOnList(targetToAdd);
                    targetToAdd.addToRequiredForList(target);
                } else {
                    error = true;
                }
            } else {
                if (!targetToAdd.isInRequiredForList(target)) {
                    target.addToRequiredForList(targetToAdd);
                    targetToAdd.addToDependsOnList(target);
                } else {
                    error = true;
                }
            }
            if (error == true) {
                String newError = "Invalid interdependence between " + target.getName() + " and " + targetToAdd.getName();
                errors.add(newError);
            }
        }
    }

    public static List<TargetDTO> getList(){
        Target target = new Target("yarin");
        List<TargetDTO> targetDTOS = new ArrayList<>();
        for (int i = 0; i<5;i++){
            targetDTOS.add(new TargetDTO(target));
        }
        return targetDTOS;
    }

}
