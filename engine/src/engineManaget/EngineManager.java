package engineManaget;

import Enums.DependencyTypes;
import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import exceptions.GeneralException;
import exceptions.MenuOptionException;
import graph.Graph;
import scema.generated.*;
import Enums.SimulationEntryPoint;
import target.Target;
import xml.Xml;
import exceptions.XmlException;

import java.util.*;

public class EngineManager implements EngineManagerInterface{
    private Graph graph;

    @Override
    public void load(String filePath) throws XmlException {
        GraphDTO graphDTO = new GraphDTO(loadHelper(filePath));

    }


    @Override
    public GraphDTO getGraphGeneralInfo() throws MenuOptionException {
        checkRunXml();
        GraphDTO graphDTO = new GraphDTO(this.graph);
        return graphDTO;
    }

    @Override
    public TargetDTO getTargetInfo(String targetName) throws MenuOptionException{
        checkRunXml();
        Target target = this.graph.getTargetByName(targetName);
        if(target == null)
            throw new MenuOptionException("The target doesn't exist on the graph.");
        else
            return new TargetDTO(target);
    }

    @Override
    public List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection) throws MenuOptionException{
        checkRunXml();
        Target targetOne, targetTwo;
        List<List<Target>> targetList;
        targetOne = new Target(this.graph.getTargetByName(src));
        targetTwo = new Target(this.graph.getTargetByName(des));
        if(targetOne == null && targetTwo == null)
            throw new MenuOptionException("Targets "+ src +" and "+ des+" doesn't exist on the graph.");
        else if(targetOne == null)
            throw new MenuOptionException("Target "+ src +" doesn't exist on the graph.");
        else if(targetTwo == null)
            throw new MenuOptionException("Target "+ des +" doesn't exist on the graph.");
        if(typeOfConnection.equals(DependencyTypes.DEPENDS_ON.toString()))
            targetList = this.graph.findAllPaths(targetOne,targetTwo);
        else
            targetList = this.graph.findAllPaths(targetTwo,targetOne);
        return getTargetDTOPath(targetList);
    }

    /* the function return list of targetDTO list from list of target list */
    private List<List<TargetDTO>> getTargetDTOPath(List<List<Target>> other){
        List<List<TargetDTO>> listDTO = new ArrayList<>();
        for(List<Target> lstTarget: other){
            List<TargetDTO> lstTargetDTO = new ArrayList<>();
            for(Target target: lstTarget){
                TargetDTO targetDTO = new TargetDTO(target);
                lstTargetDTO.add(targetDTO);
            }
            listDTO.add(lstTargetDTO);
        }
        return listDTO;
    }

    @Override
    public SimulationSummeryDTO runSimulate(SimulationEntryPoint entryPoint) {
        return null;
    }

    @Override
    public LinkedHashSet<TargetDTO> getTargetCircle(String targetName) throws MenuOptionException {
        checkRunXml();
        Target target = new Target(graph.getTargetByName(targetName));
        if(target == null)
            throw new MenuOptionException("The target doesn't exist on the graph.");
        LinkedHashSet<Target> linkedHashSet = this.graph.findCircle(target);
        return getTargetDTOCircle(linkedHashSet);
    }

    /* the function return linked hash set of targetDTO from linked hash set of target */
    private LinkedHashSet<TargetDTO> getTargetDTOCircle(LinkedHashSet<Target> other){
        LinkedHashSet<TargetDTO> listDTO = new LinkedHashSet();
        for(Target target: other){
            TargetDTO targetDTO = new TargetDTO(target);
            listDTO.add(targetDTO);
        }
        return listDTO;
    }

    /* the function throws menu exception if the xml doesn't upload */
    private void checkRunXml() throws MenuOptionException{
        if(this.graph == null){
            throw new MenuOptionException("You need to load xml file first");
        }
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
        for(Target target: targetsNames.values()){
            graph.addToGr(target);
        }
        if(!errors.isEmpty()){
            throw new XmlException(errors.toString());
        }
        return graph;
    }
}
