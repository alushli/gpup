package engineManager;

import Enums.DependencyTypes;
import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import exceptions.MenuOptionException;
import graph.Graph;
import scema.generated.*;
import Enums.SimulationEntryPoint;
import target.Target;
import task.SimulationTask;
import xml.Xml;
import exceptions.XmlException;

import java.util.*;

public class EngineManager implements EngineManagerInterface{
    private Graph graph;

    @Override
    /* the function load the graph */
    public void load(String filePath) throws XmlException {
        Graph graph = loadHelper(filePath);
        if(graph != null)
            this.graph = graph;
    }

    @Override
    /* the function load saved system status */
    public void loadSavedSystemStatus(String filePath) throws XmlException {
        filePath = filePath+".xml";
        load(filePath);
    }

    @Override
    /* the function save the system status to file */
    public void saveSystemStatus(String filePath) {
        GPUPDescriptor gpupDescriptor = new GPUPDescriptor();
        if(graph != null) {
            GPUPConfiguration gpupConfiguration = new GPUPConfiguration();
            gpupConfiguration.setGPUPGraphName(this.graph.getGraphName());
            gpupConfiguration.setGPUPWorkingDirectory(this.graph.getWorkingDirectory());
            gpupDescriptor.setGPUPConfiguration(gpupConfiguration);
            GPUPTargets gpupTargets = new GPUPTargets();
            gpupTargets.setGpupTarget(setTargetListForFile());
            gpupDescriptor.setGPUPTargets(gpupTargets);
        }
        saveSystemStatusHelper(gpupDescriptor, filePath);
    }

    private List<GPUPTarget> setTargetListForFile(){
        List<GPUPTarget> gpupTargetList = new ArrayList<>();
        for (Map.Entry<Target, Set<Target>> entry : this.graph.getGraphMap().entrySet()) {
            Target target = entry.getKey();
            GPUPTarget gpupTarget = new GPUPTarget();
            gpupTarget.setName(target.getName());
            if (target.getGeneralInfo() != null)
                gpupTarget.setGPUPUserData(target.getGeneralInfo());
            if (!target.getDependsOnList().isEmpty() || !target.getRequiredForList().isEmpty()) {
                GPUPTargetDependencies gpupTargetDependencies = new GPUPTargetDependencies();
                gpupTargetDependencies.setGpugDependency(setDependencyListForFile(target));
                gpupTarget.setGPUPTargetDependencies(gpupTargetDependencies);
            }
            gpupTargetList.add(gpupTarget);
        }
        return gpupTargetList;
    }

    private List<GPUPTargetDependencies.GPUGDependency> setDependencyListForFile(Target target){
        List<GPUPTargetDependencies.GPUGDependency> gpugDependencyList = new ArrayList<>();
        for (Target dependTarget : target.getDependsOnList()) {
            GPUPTargetDependencies.GPUGDependency gpugDependency = new GPUPTargetDependencies.GPUGDependency();
            gpugDependency.setType(DependencyTypes.DEPENDS_ON.toString());
            gpugDependency.setValue(dependTarget.getName());
            gpugDependencyList.add(gpugDependency);
        }
        for (Target requiredTarget : target.getRequiredForList()) {
            GPUPTargetDependencies.GPUGDependency gpugDependency = new GPUPTargetDependencies.GPUGDependency();
            gpugDependency.setType(DependencyTypes.REQUIRED_FOR.toString());
            gpugDependency.setValue(requiredTarget.getName());
            gpugDependencyList.add(gpugDependency);
        }
        return gpugDependencyList;
    }

    private void saveSystemStatusHelper(GPUPDescriptor gpupDescriptor, String filePath) {
        Xml.writeToXml(filePath, gpupDescriptor);
    }

    @Override
    /* the function return graph info */
    public GraphDTO getGraphGeneralInfo() throws MenuOptionException {
        GraphDTO graphDTO = new GraphDTO(this.graph);
        return graphDTO;
    }

    @Override
    /* the function return target info */
    public TargetDTO getTargetInfo(String targetName) throws MenuOptionException{
        Target target = this.graph.getTargetByName(targetName);
        if(target == null)
            throw new MenuOptionException("The target doesn't exist on the graph.");
        else
            return new TargetDTO(target);
    }

    @Override
    /* the function return targets paths */
    public List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection) throws MenuOptionException{
        Target targetOne, targetTwo;
        List<List<Target>> targetList;
        targetOne = this.graph.getTargetByName(src);
        targetTwo = this.graph.getTargetByName(des);
        if(targetOne == null && targetTwo == null)
            throw new MenuOptionException("Targets "+ src +" and "+ des+" doesn't exist on the graph.");
        else if(targetOne == null)
            throw new MenuOptionException("Target "+ src +" doesn't exist on the graph.");
        else if(targetTwo == null)
            throw new MenuOptionException("Target "+ des +" doesn't exist on the graph.");
        if(typeOfConnection.equals("D"))
            targetList = this.graph.findAllPaths(targetOne,targetTwo);
        else if(typeOfConnection.equals("R"))
            targetList = this.graph.findAllPaths(targetTwo,targetOne);
        else
            throw new MenuOptionException("Please enter valid dependency type (R/D).");
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
    /* the function return simulation info */
    public SimulationSummeryDTO runSimulate(int processTime, double chanceTargetSuccess,double chanceTargetWarning, boolean isRandom, SimulationEntryPoint entryPoint) {
        SimulationTask simulationTask = new SimulationTask();
        return simulationTask.run(this.graph, processTime,chanceTargetSuccess, chanceTargetWarning, isRandom);
    }

    @Override
    /* the function return target circle */
    public LinkedHashSet<TargetDTO> getTargetCircle(String targetName) throws MenuOptionException {
        Target target = graph.getTargetByName(targetName);
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
    public void checkRunXml() throws MenuOptionException{
        if(this.graph == null){
            throw new MenuOptionException(MenuOptionException.getXmlLoadError());
        }
    }

    /* the function return map of targets */
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

    /* the function return load the graph and return him */
    private Graph loadHelper(String filePath) throws XmlException {
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
