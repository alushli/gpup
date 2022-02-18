package engineManager;

import User.Worker;
import XmlUtils.XmlUtils;
import dtoObjects.*;
import newEnums.TargetAllStatuses;
import newEnums.TaskStatus;
import newEnums.TasksName;
import newExceptions.XmlException;
import graph.Graph;
import scema.generated.*;
import target.Target;
import task.CompilationTaskOperator;
import task.SimulationTaskOperator;
import task.TaskOperator;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class EngineManager {
    private Map<String, Graph> graphMap = new HashMap<>();
    private Map<String, TaskOperator> taskOperatorMap= new HashMap<>();


    public synchronized void addTask(TaskOperator taskOperator){
        this.taskOperatorMap.put(taskOperator.getName(), taskOperator);
    }

    public synchronized void addGraph(Graph graph) {
        this.graphMap.put(graph.getGraphName(), graph);
    }

    public synchronized Graph getGraph(String name){
        if(graphMap.containsKey(name))
            return graphMap.get(name);
        else
            return null;
    }

    public synchronized TaskOperator getTaskByName(String taskName){
        return this.taskOperatorMap.get(taskName);
    }

    /* the function load the graph */
    public synchronized void load(InputStream in, Set<String> errors) {
        try{
            Graph graph = loadHelper(in,errors);
            if(errors.isEmpty()){
                    this.graphMap.put(graph.getGraphName(), graph);
            }
        }catch (XmlException e){
            return;
        }

    }

    /* the function return map of targets (0 - origin, 1- incremental) */
    private Map<String, Target> getMapOfTargets(GPUPTargets targets, Set<String> errors) {
        GPUPTarget targetError = null;
        Map<String, Target> map = new HashMap<>();
        for (GPUPTarget gpupTarget : targets.getGPUPTarget()) {
            if (!map.keySet().contains(gpupTarget.getName())) {
                addToMap(gpupTarget,map);
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
        return map;
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
            updateTargetLists(gpupTarget, map, errors);
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

    /* the function return target circle */
    public TargetsPathDTO getTargetCircle(String targetName, String graphName)  {
        LinkedHashSet<Target> linkedHashSet;
        if(!this.graphMap.containsKey(graphName)){
            return null;
        }else{
            Graph graph = graphMap.get(graphName);
            Target target = graph.getTargetByName(targetName.toUpperCase());
            linkedHashSet = graph.findCircle(target);
        }
        return new TargetsPathDTO(linkedHashSet);
    }


    private Graph loadHelper(InputStream filePath, Set<String> errors)throws XmlException{
        GPUPDescriptor root = (GPUPDescriptor) XmlUtils.readFromXml(filePath, new GPUPDescriptor());
        Graph graphOrigin;
        if (root.getGPUPTargets() != null && root.getGPUPConfiguration() != null){
            Map<String, Target> map = getMapOfTargets(root.getGPUPTargets(), errors);
            //pricing handle
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
                graphOrigin = new Graph(root.getGPUPConfiguration().getGPUPGraphName(),simulationPrice,compilationPrice);
            if(errors.isEmpty()){
                addToTargetList(map, root,graphOrigin,true,errors);
            }else{
                throw new XmlException(errors.toString());
            }
        }else{
            throw new XmlException("The file doesn't fit the schema.");
        }
        return graphOrigin;
    }

    /* the function return targets paths */
    public List<TargetsPathDTO> getTargetsPath(String graphName,String src, String des, String typeOfConnection) {
        Target targetOne, targetTwo;
        List<List<Target>> targetList = null;
        Graph graph;
        if (this.graphMap.containsKey(graphName)) {
            graph = this.graphMap.get(graphName);
            targetOne = graph.getTargetByName(src);
            targetTwo = graph.getTargetByName(des);
            if (targetOne == null || targetTwo == null)
                return null;
            else {
                if (typeOfConnection.equalsIgnoreCase("D"))
                    targetList = graph.findAllPaths(targetOne, targetTwo);
                else if (typeOfConnection.equalsIgnoreCase("R")) {
                    targetList = graph.findAllPaths(targetTwo, targetOne);
                    reverseLists(targetList);
                }
            }
        }
        return getTargetDTOPath(targetList);
    }

    private List<TargetsPathDTO> getTargetDTOPath(List<List<Target>> lists){
        List<TargetsPathDTO> dtoList = new ArrayList<>();
        if(lists != null){
            for (List<Target> list: lists){
                dtoList.add(new TargetsPathDTO(list));
            }
        }
        return dtoList;

    }


    private void reverseLists(List<List<Target>> lists){
        for (List<Target> list : lists){
            Collections.reverse(list);
        }
    }

    public List<TargetDTO> getAllTargetsByGraph(String graphName){
        Graph graph = null;
        List<TargetDTO> targets = new ArrayList<>();
        if(this.graphMap.containsKey(graphName)){
            graph = this.graphMap.get(graphName);
            for (Map.Entry<Target, Set<Target>> entry : graph.getGraphMap().entrySet()){
                targets.add(new TargetDTO(entry.getKey()));
            }
        }
        return targets;
    }

    public synchronized List<GraphDTO> getAllGraphs(){
        List<GraphDTO> graphDTOS = new ArrayList<>();
        for (String name : this.graphMap.keySet()){
            graphDTOS.add(new GraphDTO(name, 0,0));
        }
        return graphDTOS;
    }

    public synchronized TargetDTO getTarget(String graphName, String targetName){
        if(this.graphMap.containsKey(graphName)){
            Graph graph = this.graphMap.get(graphName);
            Target target = graph.getTargetByName(targetName);
            if(target != null)
                 return new TargetDTO(target);
        }
        return null;
    }

    public synchronized List<TaskDTO> getAllTasks(){
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (String name : this.taskOperatorMap.keySet()){
            taskDTOS.add(new TaskDTO(this.taskOperatorMap.get(name)));
        }
        return taskDTOS;
    }

    public synchronized List<TaskDTO> getAllDoneOrCancelTasks(){
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (String name : this.taskOperatorMap.keySet()){
            if(this.taskOperatorMap.get(name).getTaskStatus().equals(TaskStatus.DONE)){
                taskDTOS.add(new TaskDTO(this.taskOperatorMap.get(name)));
            }
        }
        return taskDTOS;
    }

    public synchronized boolean isTaskExist(String taskName){
        return this.taskOperatorMap.containsKey(taskName);
    }

    public synchronized void subscribeUser(Worker worker, String taskName){
        TaskOperator taskOperator=  this.taskOperatorMap.get(taskName);
        if(taskOperator != null){
            taskOperator.addWorker(worker);
            worker.addToTasks(taskOperator);
        }
    }
    public synchronized void unsubscribeUser(Worker worker, String taskName){
        TaskOperator taskOperator=  this.taskOperatorMap.get(taskName);
        if(taskOperator != null){
            taskOperator.removeWorker(worker);
            worker.removeTask(taskOperator);
        }
    }

    public synchronized void pauseTask(Worker worker, String taskName){
        TaskOperator taskOperator=  this.taskOperatorMap.get(taskName);
        if(taskOperator != null){
            worker.makePause(taskOperator);
        }
    }

    public synchronized void resumeTask(Worker worker, String taskName){
        TaskOperator taskOperator=  this.taskOperatorMap.get(taskName);
        if(taskOperator != null){
            worker.makeStart(taskOperator);
        }
    }

    public synchronized void taskAction(String taskName, String action){
        TaskOperator taskOperator = this.taskOperatorMap.get(taskName);
        if(taskOperator != null){
            if(action.equals("start")){
                taskOperator.setStart();
            }else if(action.equals("pause")){
                taskOperator.setPaused();
            }else if(action.equals("cancel")){
                taskOperator.setCancel();
            }else if(action.equals("resume")){
                taskOperator.setResume();
            }
        }
    }

    public synchronized TaskRuntimeForAdmin getTaskForAdmin(String taskName){
        if(this.taskOperatorMap.containsKey(taskName)){
            return new TaskRuntimeForAdmin(this.taskOperatorMap.get(taskName));
        }
        else{
            return null;
        }
    }

    public void updateTarget(TargetUpdate targetUpdate, String worker){
        TaskOperator taskOperator;
        synchronized (this){
            taskOperator = this.taskOperatorMap.get(targetUpdate.getTaskName());
        }
        if(taskOperator != null){
            String targetStatus = targetUpdate.getStatus();
            if(targetStatus.equalsIgnoreCase("warning")){
                taskOperator.handleTargetFinish(targetUpdate.getTargetName(), TargetAllStatuses.WARNING);
            }else if(targetStatus.equalsIgnoreCase("success")){
                taskOperator.handleTargetFinish(targetUpdate.getTargetName(), TargetAllStatuses.SUCCESS);
            }else{
                taskOperator.handleTargetFinish(targetUpdate.getTargetName(), TargetAllStatuses.FAILED);
            }
            taskOperator.updateTargetWorker(targetUpdate.getTargetName(), worker);
        }
        taskOperator.createAndWriteToFile(targetUpdate.getLogs(), targetUpdate.getTargetName());
    }

    public void createGpupFolder() {
        Path path = Paths.get("c:\\gpup-working-dir");
        File folder = new File(path.toUri());
        folder.mkdir();
    }

    public synchronized void duplicateList(String name, String admin, boolean fromScratch){
        TaskOperator taskOperator = this.taskOperatorMap.get(name);
        if(taskOperator instanceof CompilationTaskOperator){
            CompilationTaskOperator compilationTaskOperator = (CompilationTaskOperator)taskOperator;
            CompilationTaskOperator newCompilationTaskOperator = new CompilationTaskOperator(compilationTaskOperator, admin, fromScratch);
            this.taskOperatorMap.put(newCompilationTaskOperator.getName(), newCompilationTaskOperator);
        }else{
            SimulationTaskOperator simulationTaskOperator = (SimulationTaskOperator) taskOperator;
            SimulationTaskOperator simulationTaskOperator1 = new SimulationTaskOperator(simulationTaskOperator, admin, fromScratch);
            this.taskOperatorMap.put(simulationTaskOperator1.getName(), simulationTaskOperator1);
        }
    }
}
