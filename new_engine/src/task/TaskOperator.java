package task;

import User.Admin;
import User.Worker;
import dtoObjects.TargetRunFX;
import graph.Graph;
import newEnums.*;
import target.Target;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskOperator {
    protected String name;
    protected Graph origin;
    protected Graph workGraph;
    protected String admin;
    protected Map<String,Worker> workerMap;
    protected int pricePerTarget;
    protected TasksName tasksName;
    protected Map<String,Target> success;
    protected Map<String,Target> waiting;
    protected Map<String,Target> frozen;
    protected Map<String,Target> failed;
    protected Map<String,Target> skipped;
    protected Map<String,Target> inProcess = new HashMap<>();
    protected Map<String, TargetAllStatuses> targetStatusMap;
    protected TaskStatus taskStatus;
    protected int numOfTargetForFinish;
    protected int numOfCopyTasks = 0;
    protected String path;
    protected List<String> logs = new ArrayList<>();
    protected Map<String, String> targetToWorker = new HashMap<>();

    public synchronized Target getTargetToRun(){
        Target target = null;
        if(taskStatus.equals(TaskStatus.IN_PROCESS)){
            for (Map.Entry<String, Target> entry : this.waiting.entrySet()){
                target = entry.getValue();
                if(target != null){
                    break;
                }
            }
        }
        if(target != null){
            this.targetStatusMap.put(target.getName(), TargetAllStatuses.IN_PROCESS);
            this.waiting.remove(target.getName());
            this.inProcess.put(target.getName(), target);
        }
        return target;
    }

    public int getNumOfTargetForFinish() {
        return numOfTargetForFinish;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public synchronized void handleTargetFinish(String targetName, TargetAllStatuses runStatus){
        this.logs.add("Target " + targetName + " finish run with status:" +runStatus.toString());
        Target target = this.workGraph.getTargetByName(targetName);
        this.targetStatusMap.put(targetName.trim().toUpperCase(), runStatus);
        if(runStatus == TargetAllStatuses.FAILED){
            handleFail(target);
        }else if(runStatus == TargetAllStatuses.WARNING || runStatus == TargetAllStatuses.SUCCESS){
            handleSuccess(target);
        }
    }

    private synchronized void handleFail(Target target){
        Set<Target> targets = target.getRequiredForList();
        this.failed.put(target.getName(), target);
        this.targetStatusMap.put(target.getName(), TargetAllStatuses.FAILED);
        decrease();
        for (Target target1 : targets){
            handleFailHelper(target1);
        }
    }

    public synchronized void decrease(){
        this.numOfTargetForFinish--;
        if(numOfTargetForFinish == 0){
            taskStatus = TaskStatus.DONE;
        }
    }

    private synchronized void handleFailHelper(Target target){
        if(this.workGraph.isContainTarget(target)){
            this.frozen.remove(target);
            if(!this.skipped.containsKey(target.getName())){
                this.logs.add("Target " + target.getName() + " turned skipped!");
                this.skipped.put(target.getName(), target);
                decrease();
            }
            this.targetStatusMap.put(target.getName(), TargetAllStatuses.SKIPPED);
            Set<Target> targets= target.getRequiredForList();
            for (Target target1: targets){
                handleFailHelper(target1);
            }
        }
    }

    private synchronized void handleSuccess(Target target){
        Set<Target> targets = target.getRequiredForList();
        decrease();
        for (Target target1 : targets){
            this.workGraph.removeConnectionIfExist(target1, target);
            if(workGraph.isRunnable(target1)){
                this.targetStatusMap.put(target1.getName(), TargetAllStatuses.WAITING);
                this.frozen.remove(target1.getName());
                this.waiting.put(target1.getName(), target1);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getPricePerTarget() {
        return pricePerTarget;
    }

    public Graph getOrigin() {
        return origin;
    }

    public Graph getWorkGraph() {
        return workGraph;
    }

    public String getAdmin() {
        return admin;
    }

    public Map<String, Worker> getWorkerMap() {
        return workerMap;
    }

    public TasksName getTasksName() {
        return tasksName;
    }

    public Map<String, Target> getSuccess() {
        return success;
    }

    public Map<String, Target> getWaiting() {
        return waiting;
    }

    public Map<String, Target> getFrozen() {
        return frozen;
    }

    public Map<String, Target> getFailed() {
        return failed;
    }

    public Map<String, Target> getSkipped() {
        return skipped;
    }

    public Map<String, TargetAllStatuses> getTargetStatusMap() {
        return targetStatusMap;
    }

    public synchronized void setPaused(){
        this.taskStatus = TaskStatus.PAUSED;
    }

    public synchronized void setResume(){
        this.taskStatus = TaskStatus.IN_PROCESS;
    }

    public synchronized void setStart(){
        if(this.taskStatus == TaskStatus.FROZEN){
            List<Target> runnable = workGraph.getRunnableTargets();
            for (Target target : runnable){
                frozen.remove(target.getName());
                waiting.put(target.getName(), target);
                this.targetStatusMap.put(target.getName(), TargetAllStatuses.WAITING);
            }
            this.taskStatus = TaskStatus.IN_PROCESS;
        }

    }

    public synchronized void setCancel(){
        this.taskStatus = TaskStatus.CANCLE;
    }

    public synchronized void addWorker(Worker worker){
        this.workerMap.put(worker.getName(), worker);
    }

    public synchronized void removeWorker(Worker worker){
        this.workerMap.remove(worker.getName());
    }

    public synchronized List<TargetRunFX> getAllWithStatus(){
        List<TargetRunFX> targets = new ArrayList<>();
        for (Map.Entry<String, TargetAllStatuses> entry : this.targetStatusMap.entrySet()){
            targets.add(new TargetRunFX(entry.getKey(), this.workGraph.getTargetByName(entry.getKey()).getPosition().toString(), getWorker(entry.getKey()), entry.getValue().toString(), entry.getValue().toString()));
        }
        return targets;
    }

    public synchronized void upCopy(){
        this.numOfCopyTasks++;
    }

    public int getNumOfCopyTasks(){
        return numOfCopyTasks;
    }

    public Set<String> getAllSkipAndFail(){
        Set<String> failAndSkip = new HashSet<>();
        for (String target : this.skipped.keySet()){
            failAndSkip.add(target);
        }
        for (String target : this.failed.keySet()){
            failAndSkip.add(target);
        }
        return failAndSkip;
    }

    public String createFolder(TasksName taskName, String rootDirectory) throws FileSystemException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        String date = simpleDateFormat.format(new Date());
        String fileName = rootDirectory + "/" + taskName + " " + date; // Desired pattern

        File folder = new File(fileName);

        if (folder.mkdirs()) {                          // If a folder was created
            return folder.getAbsolutePath();
        } else {
            throw new FileSystemException("Folder was not created successfully.");
        }
    }

    public String getPath() {
        return path;
    }

    public void createAndWriteToFile(List<String> logs, String targetName){
        String targetPathFile = createTargetFile(targetName);
        printTargetToFile(logs, targetPathFile);
    }

    /* the function create target simulation files */
    protected String createTargetFile(String targetName){
        try {
            Path path = Paths.get(this.path, targetName + ".txt");
            File file = new File(path.toUri());
            file.createNewFile();
            return path.toUri().getPath();
        } catch (Exception e){
            return null;
        }
    }

    /* the function write to target simulation file */
    protected void printTargetToFile(List<String> logs, String path){
        try {
            FileWriter file = new FileWriter(path, true);
            for(String str:logs) {
                file.write(str + "\n\r");
            }
            file.close();
        }catch (Exception e){}
    }

    public List<String> getLogs() {
        return logs;
    }

    public synchronized void updateTargetWorker(String targetName, String workerName){
        this.targetToWorker.put(targetName, workerName);
    }

    public synchronized String getWorker(String target){
        if(this.targetToWorker.containsKey(target)){
            return this.targetToWorker.get(target);
        }
        return null;
    }

}
