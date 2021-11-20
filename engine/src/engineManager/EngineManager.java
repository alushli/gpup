package engineManager;

import Enums.DependencyTypes;
import Enums.TasksName;
import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import exceptions.MenuOptionException;
import exceptions.TaskException;
import graph.Graph;
import scema.generated.*;
import Enums.SimulationEntryPoint;
import target.Target;
import task.SimulationTask;
import xml.Xml;
import exceptions.XmlException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class EngineManager implements EngineManagerInterface{
    private Graph graph;

    @Override
    /* the function load the graph */
    public void load(String filePath) throws XmlException {
        List<Graph> graphsList = loadHelper(filePath);
        Graph graphOrigin = graphsList.get(0);
        Graph graphIncremental = graphsList.get(1);
        if(graphOrigin != null)
            this.graph = graphOrigin;
        if(graphIncremental != null)
            SimulationTask.graphStatic = graphIncremental;
    }

    public void saveSimulationFolder()  {
            Graph graph = SimulationTask.graphStatic;
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH.mm.ss");
            String strDate = simpleDateFormat.format(date);
            File folder = new File(graph.getWorkingDirectory()+ "/" + TasksName.SIMULATION + "-" + strDate);
            if(folder.mkdir())
                System.out.println("yes");
            //******************

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
            List<GPUPTarget> gpupTargetList = new ArrayList<>();
            setTargetListGraphForFile(gpupTargetList, this.graph, true);
            setTargetListGraphForFile(gpupTargetList, SimulationTask.graphStatic, false);
            gpupTargets.setGpupTarget(gpupTargetList);
            gpupDescriptor.setGPUPTargets(gpupTargets);
        }
        saveSystemStatusHelper(gpupDescriptor, filePath);
    }

    private void setTargetListGraphForFile(List<GPUPTarget> gpupTargetList, Graph graph, boolean isOrigin){
        for (Map.Entry<Target, Set<Target>> entry : graph.getGraphMap().entrySet()) {
            Target target = entry.getKey();
            GPUPTarget gpupTarget = new GPUPTarget();
            gpupTarget.setName(target.getName());
            if(!isOrigin)
                gpupTarget.setType(SimulationEntryPoint.INCREMENTAL.toString());
            if (target.getGeneralInfo() != null)
                gpupTarget.setGPUPUserData(target.getGeneralInfo());
            if (!target.getDependsOnList().isEmpty() || !target.getRequiredForList().isEmpty()) {
                GPUPTargetDependencies gpupTargetDependencies = new GPUPTargetDependencies();
                gpupTargetDependencies.setGpugDependency(setDependencyListForFile(target));
                gpupTarget.setGPUPTargetDependencies(gpupTargetDependencies);
            }
            gpupTargetList.add(gpupTarget);
        }
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
        if(typeOfConnection.equalsIgnoreCase("D"))
            targetList = this.graph.findAllPaths(targetOne,targetTwo);
        else if(typeOfConnection.equalsIgnoreCase("R"))
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

    /* the function return map of targets (0 - origin, 1- incremental) */
    private List<Map<String, Target>> getMapsOfTargets(GPUPTargets targets, Set<String > errors) {
        GPUPTarget targetError = null;
        List<Map<String, Target>> mapsList = new ArrayList<>();
        mapsList.add(new HashMap<>());
        mapsList.add(new HashMap<>());
        for (GPUPTarget gpupTarget : targets.getGPUPTarget()) {
            if (gpupTarget.getType() != null) {
                if (!mapsList.get(1).keySet().contains(gpupTarget.getName())) {
                    addToMap(gpupTarget, mapsList.get(1));
                } else
                    targetError = gpupTarget;
            } else {
                if (!mapsList.get(1).keySet().contains(gpupTarget.getName())) {
                    addToMap(gpupTarget, mapsList.get(0));
                } else
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



    private void addToMap(GPUPTarget gpupTarget, Map<String, Target> map){
        Target target = new Target(gpupTarget.getName());
        map.put(gpupTarget.getName(), target);
        if(gpupTarget.getGPUPUserData() != null) {
            target.updateGeneralInfo(gpupTarget.getGPUPUserData());
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

    /* the function return load the graph and return him (0 - origin, 1- incremental)  */
    private List<Graph> loadHelper(String filePath) throws XmlException {
        List<Graph> graphsList = new ArrayList<>();
        GPUPDescriptor root = (GPUPDescriptor) Xml.readFromXml(filePath, new GPUPDescriptor());
        Set<String> errors = new HashSet<>();
        List<Map<String, Target>> mapsList = getMapsOfTargets(root.getGPUPTargets(), errors);
        Graph graphOrigin = new Graph(root.getGPUPConfiguration().getGPUPGraphName(), root.getGPUPConfiguration().getGPUPWorkingDirectory());
        Graph graphIncremental = new Graph(root.getGPUPConfiguration().getGPUPGraphName(), root.getGPUPConfiguration().getGPUPWorkingDirectory());
        if (errors.isEmpty()) {
            addToTargetList(mapsList.get(0), root, graphOrigin, true, errors);
            addToTargetList(mapsList.get(1), root, graphIncremental, false, errors);
        }
        if(!errors.isEmpty()){
            throw new XmlException(errors.toString());
        }
        graphsList.add(graphOrigin);
        graphsList.add(graphIncremental);
        return graphsList;
    }

    private void addToTargetList(Map<String, Target> map, GPUPDescriptor root, Graph graph, boolean isOrigin, Set<String> errors) {
        for(GPUPTarget gpupTarget : root.getGPUPTargets().getGPUPTarget()){
            if(!isOrigin){
                if(gpupTarget.getType() != null){
                    addTargetToGraph(gpupTarget, map, errors);
                    graph.addToGr(map.get(gpupTarget.getName()));
                }
            } else{
                if(gpupTarget.getType() == null){
                    addTargetToGraph(gpupTarget, map, errors);
                    graph.addToGr(map.get(gpupTarget.getName()));
                }
            }
        }
    }

    private void addTargetToGraph(GPUPTarget gpupTarget, Map<String, Target> map, Set<String> errors){
        Target target = map.get(gpupTarget.getName());
        if(gpupTarget.getGPUPTargetDependencies() != null){
            for (GPUPTargetDependencies.GPUGDependency dependency : gpupTarget.getGPUPTargetDependencies().getGPUGDependency()) {
                if (map.keySet().contains(dependency.getValue())) {
                    addToTargetListByType(target, dependency, map.get(dependency.getValue()), errors);
                } else {
                    String newError = target.getName() + " " + dependency.getType() + " " + dependency.getValue()
                            + "but" + dependency.getValue() + " not exist";
                    errors.add(newError);
                }
            }
        }
    }
}


