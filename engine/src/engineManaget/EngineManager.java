package engineManaget;

import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import graph.Graph;
import scema.generated.*;
import target.SimulationEntryPoint;
import target.Target;
import xml.Xml;
import exceptions.XmlException;

import java.util.*;

public class EngineManager implements EngineManagerInterface{

    @Override
    public void load(String filePath) throws XmlException {
        GraphDTO graphDTO = new GraphDTO(loadHelper(filePath));

    }

    @Override
    public GraphDTO getGraphGeneralInfo() {
        return null;
    }

    @Override
    public TargetDTO getTargetInfo(String targetName) {
        return null;
    }

    @Override
    public List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection) {
        return null;
    }

    @Override
    public SimulationSummeryDTO runSimulate(SimulationEntryPoint entryPoint) {
        return null;
    }

    private Map<String, Target> getMapOfTargets(GPUPTargets targets, Set<String > errors){
        Map<String, Target> map = new HashMap<>();
        for (GPUPTarget gpupTarget : targets.getGPUPTarget()){
            if(!map.keySet().contains(gpupTarget.getName())){
                Target target = new Target(gpupTarget.getName());
                map.put(gpupTarget.getName(), target);
                if(gpupTarget.getGPUPUserData() != null) {
                    target.updateGeneralInfo(gpupTarget.getGPUPUserData());
                }
            }else{
                String error = new String();
                error += "Target:" + gpupTarget.getName();
                error+= " appears more than one";
                errors.add(error);
            }
        }
        return map;
    }

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

    public Graph loadHelper(String filePath) throws XmlException {
        GPUPDescriptor root = (GPUPDescriptor) Xml.readFromXml(filePath, new GPUPDescriptor());
        Set<String> errors = new HashSet<>();
        Map<String, Target> targetsNames = getMapOfTargets(root.getGPUPTargets(), errors);
        Graph graph = new Graph(root.getGPUPConfiguration().getGPUPGraphName(), root.getGPUPConfiguration().getGPUPWorkingDirectory());
        if (errors.isEmpty()) {
            for (GPUPTarget gpupTarget : root.getGPUPTargets().getGPUPTarget()) {
                Target target = targetsNames.get(gpupTarget.getName());
                if(gpupTarget.getGPUPTargetDependencies() != null){
                    for (GPUPTargetDependencies.GPUGDependency dependency : gpupTarget.getGPUPTargetDependencies().getGPUGDependency()) {
                        if (targetsNames.keySet().contains(dependency.getValue())) {
                            addToTargetListByType(target, dependency, targetsNames.get(dependency.getValue()), errors);
                        } else {
                            String newError = target.getName() + " " + dependency.getType() + " " + dependency.getValue()
                                    + "but" + dependency.getValue() + " not exist";
                        }
                    }
                }
            }
        }
        for (Target target : targetsNames.values()){
            graph.addToGr(target);
        }
        if(!errors.isEmpty()){
            throw new XmlException(errors.toString());
        }
        return graph;
    }

    /* the function return true if there is a target that doesn't exist on the file but
     other target has type of dependencies with hem and false else */
    private boolean compareValidTargetList(Set<String> validTargetUnique, Set<String> validTargetExist){
        for(String name: validTargetUnique){
            if(validTargetExist.contains(name))
                validTargetExist.remove(name);
        }
        if(validTargetExist.isEmpty())
            return true;
        return false;
    }
}
