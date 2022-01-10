package engineManager;

import Enums.DependencyTypes;
import Enums.TasksName;
import dtoObjects.*;
import exceptions.MenuOptionException;
import exceptions.TaskException;
import graph.Graph;
import graph.SerialSet;
import scema.generated.*;
import Enums.SimulationEntryPoint;
import target.Target;
import task.Task;
import task.TaskManager;
import task.compiler.CompilerTaskManager;
import task.simulation.SimulationTaskManager;
import xml.Xml;
import exceptions.XmlException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class EngineManager implements EngineManagerInterface{
    private Graph graph;//whole graph from xml
    public static Graph graphStatic; //save last graph(copy)
    private int maxThreadsForTask = 1;
    private int maxTreads;
    private SimulationTaskManager simulationTaskManager = null;
    private CompilerTaskManager compilerTaskManager = null;
    private TaskManager taskManager = null;
    private boolean isTaskRun = false;
    private Boolean synchroObj;

    public void setSimulationTaskManager(SimulationTaskManager simulationTaskManager) {
        this.simulationTaskManager = simulationTaskManager;
    }

    public void setCompilerTaskManager(CompilerTaskManager compilerTaskManager) {
        this.compilerTaskManager = compilerTaskManager;
    }

    public EngineManager(){
        this.synchroObj = new Boolean(true);
    }

    public boolean isTaskRun() {
        return isTaskRun;
    }

    public void setTaskRun(boolean taskRun) {
        isTaskRun = taskRun;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public SimulationTaskManager getSimulationTaskManager() {
        return simulationTaskManager;
    }

    public CompilerTaskManager getCompilerTaskManager() {
        return compilerTaskManager;
    }

    public int getMaxThreadsForTask() {
        return maxThreadsForTask;
    }

    @Override
    /* the function load the graph */
    public void load(String filePath) throws XmlException {
        List<Graph> graphsList = loadHelper(filePath);
        Graph graphOrigin = graphsList.get(0);
        Graph graphIncremental = graphsList.get(1);
        if(graphOrigin != null) {
            this.graph = graphOrigin;
            this.maxThreadsForTask = this.maxTreads;
        }
        if(!graphIncremental.getGraphMap().isEmpty())
            graphStatic = graphIncremental;
        else
            graphStatic = null;
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
            if(graphStatic != null)
                setTargetListGraphForFile(gpupTargetList, graphStatic, false);
            gpupTargets.setGpupTarget(gpupTargetList);
            gpupDescriptor.setGPUPTargets(gpupTargets);
        }
        saveSystemStatusHelper(gpupDescriptor, filePath);
    }

    /* the function set graph for saving on file */
    private void setTargetListGraphForFile(List<GPUPTarget> gpupTargetList, Graph graph, boolean isOrigin){
        for (Map.Entry<Target, Set<Target>> entry : graph.getGraphMap().entrySet()) {
            Target target = entry.getKey();
            GPUPTarget gpupTarget = new GPUPTarget();
            gpupTarget.setName(target.getName());
            if(!isOrigin)
                gpupTarget.setType(SimulationEntryPoint.INCREMENTAL.toString());
            if (target.getGeneralInfo() != null)
                gpupTarget.setGPUPUserData(target.getGeneralInfo());
            if(!entry.getValue().isEmpty()) {
                GPUPTargetDependencies gpupTargetDependencies = new GPUPTargetDependencies();
                gpupTargetDependencies.setGpugDependency(setDependencyListForFile(entry.getValue()));
                gpupTarget.setGPUPTargetDependencies(gpupTargetDependencies);
            }
            gpupTargetList.add(gpupTarget);
        }
    }

    /* the function return dependency list of target for saving on file */
    private List<GPUPTargetDependencies.GPUGDependency> setDependencyListForFile(Set<Target> dependsOn){
        List<GPUPTargetDependencies.GPUGDependency> gpugDependencyList = new ArrayList<>();
        for (Target dependTarget : dependsOn) {
            GPUPTargetDependencies.GPUGDependency gpugDependency = new GPUPTargetDependencies.GPUGDependency();
            gpugDependency.setType(DependencyTypes.DEPENDS_ON.toString());
            gpugDependency.setValue(dependTarget.getName());
            gpugDependencyList.add(gpugDependency);
        }
        return gpugDependencyList;
    }

    /* the function help for function saveSystemStatus */
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
        Target target = this.graph.getTargetByName(targetName.toUpperCase());
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
        else if(typeOfConnection.equalsIgnoreCase("R")){
            targetList = this.graph.findAllPaths(targetTwo,targetOne);
            reverseLists(targetList);
        }
        else
            throw new MenuOptionException("Please enter valid dependency type (R/D).");

        return getTargetDTOPath(targetList);
    }


    private void reverseLists(List<List<Target>> lists){
        for (List<Target> list : lists){
            Collections.reverse(list);
        }
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

    /* the function return simulation info */
    public TaskSummeryDTO runSimulateConsole(int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom,
                                             SimulationEntryPoint entryPoint, Consumer<String> consumer) throws TaskException {

        boolean fromScratch = entryPoint.equals(SimulationEntryPoint.FROM_SCRATCH);
        SimulationTaskManager taskManager = new SimulationTaskManager(this.graph,processTime,chanceTargetSuccess,chanceTargetWarning,
                isRandom,fromScratch,consumer,5, this.synchroObj);
        return taskManager.getSummeryDTO();
    }

    public void runSimulate(Collection<String> targets,int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom,
                                             SimulationEntryPoint entryPoint, Consumer<String> consumer, int maxParallel) throws TaskException {

        boolean fromScratch = entryPoint.equals(SimulationEntryPoint.FROM_SCRATCH);
        this.simulationTaskManager = new SimulationTaskManager(new Graph(targets,this.graph),processTime,chanceTargetSuccess,chanceTargetWarning,
                isRandom,fromScratch,consumer,maxParallel, this.synchroObj);
        this.simulationTaskManager.handleRunSimulation(processTime, chanceTargetSuccess, chanceTargetWarning, isRandom, consumer);
    }


    public void runCompiler(Collection<String> targets,String sourceFolder, String productFolder, SimulationEntryPoint entryPoint,
                            Consumer<String> consumer, int maxParallel) throws TaskException {

        boolean fromScratch = entryPoint.equals(SimulationEntryPoint.FROM_SCRATCH);
        this.compilerTaskManager = new CompilerTaskManager(new Graph(targets,this.graph), sourceFolder, productFolder,
                fromScratch,consumer,maxParallel, this.synchroObj);
        this.compilerTaskManager.handleRunCompiler(sourceFolder, productFolder, consumer);
    }

    public void changeThreadsNum(int newCount, TasksName tasksName){
        if(tasksName.equals(TasksName.SIMULATION) && this.simulationTaskManager != null)
            this.simulationTaskManager.setMaxParallel(newCount);
        else if(tasksName.equals(TasksName.COMPILATION) && this.compilerTaskManager != null)
            this.compilerTaskManager.setMaxParallel(newCount);
    }


    @Override
    /* the function return target circle */
    public LinkedHashSet<TargetDTO> getTargetCircle(String targetName) throws MenuOptionException {
        Target target = graph.getTargetByName(targetName.toUpperCase());
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
                    mapsList.get(1).put(gpupTarget.getName(), mapsList.get(0).get(gpupTarget.getName()));
                } else
                    targetError = gpupTarget;
            } else {
                if (!mapsList.get(0).keySet().contains(gpupTarget.getName())) {
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

    /* the function add the target to map */
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
        if(root.getGPUPTargets() != null && root.getGPUPConfiguration() != null) {
            this.maxTreads = root.getGPUPConfiguration().getGPUPMaxParallelism();
            List<Map<String, Target>> mapsList = getMapsOfTargets(root.getGPUPTargets(), errors);
            Graph graphOrigin = new Graph(root.getGPUPConfiguration().getGPUPGraphName(), root.getGPUPConfiguration().getGPUPWorkingDirectory());
            Graph graphIncremental = new Graph(root.getGPUPConfiguration().getGPUPGraphName(), root.getGPUPConfiguration().getGPUPWorkingDirectory());
            if (errors.isEmpty()) {
                addToTargetList(mapsList.get(0), root, graphOrigin, true, errors);
                addToTargetList(mapsList.get(1), root, graphIncremental, false, errors);
                /*Because serial sets are optional*/
                if(root.getGPUPSerialSets() != null){
                    addSerialSetsToGraph(root.getGPUPSerialSets().getGPUPSerialSet(), graphOrigin, errors);
                }
            }
            if (!errors.isEmpty()) {
                throw new XmlException(errors.toString());
            }
            graphsList.add(graphOrigin);
            graphsList.add(graphIncremental);
            return graphsList;
        } else{
            throw new XmlException("The file doesn't fit the schema.");
        }
    }

    private void addSerialSetsToGraph(List<GPUPDescriptor.GPUPSerialSets.GPUPSerialSet> sets, Graph graph,  Set<String> errors){
        if(sets != null){
            for (GPUPDescriptor.GPUPSerialSets.GPUPSerialSet set : sets){
                SerialSet newSerialSet = new SerialSet(set.getTargets(), graph,errors,set.getName());
                graph.addSerialSet(newSerialSet, errors);
                for (Target target : newSerialSet.getAllSet().values()){
                    target.addSerialSet(newSerialSet);
                }
            }
        }
    }

    /* the function add targets to target list */
    private void addToTargetList(Map<String, Target> map, GPUPDescriptor root, Graph graph, boolean isOrigin, Set<String> errors) {
        for(GPUPTarget gpupTarget : root.getGPUPTargets().getGPUPTarget()){
            if(!isOrigin){
                if(gpupTarget.getType() != null){
                    graph.addToGraphWithoutList(map.get(gpupTarget.getName()));
                    updateIncrementalGraph(gpupTarget,map,graph);
                }
            } else{
                if(gpupTarget.getType() == null){
                    updateTargetLists(gpupTarget, map, errors);
                }
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

    private void updateIncrementalGraph(GPUPTarget gpupTarget, Map<String, Target> map, Graph graph){
        if(gpupTarget.getGPUPTargetDependencies() != null){
            for (GPUPTargetDependencies.GPUGDependency dependency: gpupTarget.getGPUPTargetDependencies().getGPUGDependency()){
                String name = dependency.getValue();
                if(map.keySet().contains(name.trim())){
                    graph.addConnection(map.get(name),map.get(gpupTarget.getName()));
                }
            }
        }
    }

    public Collection<TargetFXDTO> getAllTargets(){
        Collection<TargetFXDTO> targets = new ArrayList<>();
        if(this.graph != null){
            for (Target target : graph.getGraphMap().keySet()){
                targets.add(new TargetFXDTO(target));
            }
        }
        return targets;
    }

    public void exportGraph(String path) throws IOException {
        FileWriter graphFile = new FileWriter(path+".viz");
        writeToFile(graphFile);
        graphFile.close();
        String[] command = {"cmd.exe" , "/c", "dot -Tpng " + path+".viz" + " -o " + path +".png"};
        ProcessBuilder processBuilder = new ProcessBuilder( command );
        processBuilder.start();
    }

    private void writeToFile(FileWriter file) throws IOException {
        int count = 0;
        file.write("digraph {\n");
        for (Map.Entry<Target, Set<Target>> entry : graph.getGraphMap().entrySet()) {
            Target target = entry.getKey();
            if(!target.getDependsOnList().isEmpty()){
                file.write(target.getName());
                file.write(" -> {");
                for(Target targetDep:target.getDependsOnList()){
                    count++;
                    file.write(targetDep.getName());
                    if(count < target.getDependsOnList().size())
                        file.write(", ");
                }
                count = 0;
                file.write("}\n");
            } else if(target.getDependsOnList().isEmpty() && target.getRequiredForList().isEmpty()){
                file.write(target.getName());
                file.write(" -> {}");
            }
        }
        file.write("}");
    }

    public Set<SerialSetFXDTO> getSerialSetOfGraph(){
        return this.graph.getSerialSetFXDTO();
    }

    public boolean hasSerialSet(){
        return this.graph.hesSerialSets();
    }

    public Set<String> getSerialSetOfTarget(String target){
        if(this.graph != null){
            for (Target target1 : graph.getGraphMap().keySet()){
               if(target1.getName().equals(target)){
                    return target1.getSerialSetFXDTO();
               }
            }
        }
        return null;
    }

    public Set<String> getWhatIfTargets(String name, String typeOfDependency){
        if(this.graph != null){
            for (Target target : graph.getGraphMap().keySet()){
                if(target.getName().equals(name)){
                    return target.getAllHangingByTypeOfTargets(typeOfDependency).keySet();
                }
            }
        }
        return null;
    }

    public Boolean getSynchroObj() {
        return synchroObj;
    }

    public void setTaskRuntimeDTO(TaskRuntimeDTO taskRuntimeDTO, TasksName tasksName){
        if(tasksName.equals(TasksName.SIMULATION))
            this.simulationTaskManager.setTaskRunTime(taskRuntimeDTO);
        else if(tasksName.equals(TasksName.COMPILATION))
            this.compilerTaskManager.setTaskRunTime(taskRuntimeDTO);
    }

    public void setPaused(boolean paused, String taskName) {
        if (taskName.equals("Simulation Task")) {
            if (this.simulationTaskManager != null) {
                this.simulationTaskManager.setIsPaused(paused);
            }
        } else {
            if (this.compilerTaskManager != null) {
                this.compilerTaskManager.setIsPaused(paused);
            }
        }
    }
}


