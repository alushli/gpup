import graph.Graph;
import scema.generated.*;
import target.Target;
import xml.Xml;
import exceptions.XmlException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EngineManager {

//    /* the function load new graph from xml file */
//    public Graph load(String filePath) throws XmlException {
//        Set<String> validTargetUnique = new HashSet<>();
//        Set<String> validTargetExist = new HashSet<>();
//        Set<String> errorTargetsDependencies = new HashSet<>();
//        Set<String> errorTargetsUnique = new HashSet<>();
//        Object root = Xml.readFromXml(filePath, new GPUPDescriptor());
//        GPUPConfiguration gpupConfiguration = ((GPUPDescriptor) root).getGPUPConfiguration();
//        GPUPTargets gpupTargets = ((GPUPDescriptor) root).getGPUPTargets();
//        Graph graph = new Graph(gpupConfiguration.getGPUPGraphName(), gpupConfiguration.getGPUPWorkingDirectory());
//        for(GPUPTarget gpupTarget: gpupTargets.getGPUPTarget()){
//            Target target;
//            String targetName = gpupTarget.getName();
//            if(validTargetUnique.contains(targetName))
//                errorTargetsUnique.add(targetName);
//            validTargetUnique.add(targetName);
//            validTargetExist.add(targetName);
//            if (!graph.getGraphMap().containsKey(gpupTarget))
//                graph.addToGr(new Target(targetName));
//            target = graph.getTargetByName(targetName);
//            if(gpupTarget.getGPUPUserData() != null)
//               target.updateGeneralInfo(gpupTarget.getGPUPUserData());
//            if(gpupTarget.getGPUPTargetDependencies() != null)
//                target.load(gpupTarget.getGPUPTargetDependencies(), graph, validTargetExist, errorTargetsDependencies);
//        }
//        if(!errorTargetsUnique.isEmpty())
//            throw new XmlException("targets " + errorTargetsUnique.toString() + " already exist in this file");
//        if(!compareValidTargetList(validTargetUnique, validTargetExist))
//            throw new XmlException("targets " + validTargetExist.toString() + " not exist in this file but other targets dependsOn " +
//                    "or requiredFor them");
//        if(!errorTargetsDependencies.isEmpty())
//            throw new XmlException(errorTargetsDependencies.toString());
//        return graph;
//    }

    private Map<String, Target> getSetOfTargetsNames(GPUPTargets targets, Set<String > errors){
        Map<String, Target> map = new HashMap<>();
        for (GPUPTarget gpupTarget : targets.getGPUPTarget()){
            if(!map.keySet().contains(gpupTarget.getName())){
                map.put(gpupTarget.getName(), new Target(gpupTarget.getName()));
            }else{
                String error = new String();
                error += "Target:" + gpupTarget.getName();
                error+= " appears twice";
                errors.add(error);
            }
        }
        return map;
    }

    private void addToTargetListByType(Target target, GPUPTargetDependencies.GPUGDependency dependency, Target targetToAdd,
                                       Set<String> errors){
        boolean error = false;
        if(dependency.getType().equals("dependsOn")){
            if(!targetToAdd.isInDependsOnList(target)){
                target.addToDependsOnList(targetToAdd);
                targetToAdd.addToRequiredForList(target);
            }
            else{
                error = true;
            }
        }else{
            if(!targetToAdd.isInRequiredForList(target)){
                target.addToRequiredForList(targetToAdd);
                targetToAdd.addToDependsOnList(target);
            }else{
                error = true;
            }
        }
        if(error == true){
            String newError = "Invalid interdependence between " + target.getName() + " and " + targetToAdd.getName();
            errors.add(newError);
        }
    }

    public Graph load2(String filePath) throws XmlException {
        GPUPDescriptor root = (GPUPDescriptor) Xml.readFromXml(filePath, new GPUPDescriptor());
        Set<String> errors = new HashSet<>();
        Map<String, Target> targetsNames = getSetOfTargetsNames(root.getGPUPTargets(), errors);
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
                graph.addToGr(target);
            }
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
