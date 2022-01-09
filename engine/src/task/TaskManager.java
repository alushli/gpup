package task;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import Enums.TargetStatus;
import Enums.TasksName;
import com.sun.org.apache.xpath.internal.operations.Bool;
import dtoObjects.TaskRuntimeDTO;
import dtoObjects.TaskSummeryDTO;
import engineManager.EngineManager;
import exceptions.TaskException;
import graph.Graph;
import graph.SerialSet;
import target.Target;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public abstract class TaskManager {

    protected int initSize;
    protected TaskSummeryDTO summeryDTO;
    protected int maxParallel;
    protected Set<Target> skipped;
    protected Set<Target> failed ;
    protected Set<Target> succeed ;
    protected Set<Target> warnings;
    protected ThreadPoolExecutor pool;
    protected int counter;
    protected String folderPath;
    protected TaskRuntimeDTO taskRuntimeDTO;
    protected Boolean synchroObj;
    protected boolean isPaused;
    protected Boolean isPausedObj;

    /* the function save the simulation folder */
    public String saveSimulationFolder() throws TaskException {
        Graph graph = EngineManager.graphStatic;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        String strDate = simpleDateFormat.format(date);
        Path path = Paths.get(graph.getWorkingDirectory(),TasksName.SIMULATION + "-" + strDate);
        File folder = new File(path.toUri());
        if(folder.mkdir())
            return folder.getAbsolutePath();
        else
            throw new TaskException("The path doesn't exist or has invalid characters, please change the xml and upload again.");
    }

    public synchronized void setMaxParallel(int max){
        if(max != this.maxParallel) {
            this.maxParallel = max;
            this.pool.setCorePoolSize(max);
            this.pool.setMaximumPoolSize(max);
        }
    }

    /* the function save the simulation folder */
    public String saveCompilerFolder() throws TaskException {
        Graph graph = EngineManager.graphStatic;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        String strDate = simpleDateFormat.format(date);
        Path path = Paths.get(graph.getWorkingDirectory(),TasksName.COMPILATION + "-" + strDate);
        File folder = new File(path.toUri());
        if(folder.mkdir())
            return folder.getAbsolutePath();
        else
            throw new TaskException("The path doesn't exist or has invalid characters, please change the xml and upload again.");
    }

    protected void handleFailureRec(Target target, List<Consumer<String>> consumersList , Task task,Target dad ){
        synchronized (this.synchroObj){
            this.taskRuntimeDTO.getTargetByName(target.getName()).addToSkippedBecause(dad.getName());
        }

        addToSkipped(target);
        task.writeToConsumers(consumersList,"Target: "+target.getName()+ " turned skipped (because "+ dad.getName()+" failed/skipped)" );
        target.setRunStatus(TargetRunStatus.SKIPPED);
        Set<Target> requireForTargets = target.getRequiredForList();
        for (Target target1 : requireForTargets){
            if(EngineManager.graphStatic.getGraphMap().containsKey(target1))
                 handleFailureRec(target1,consumersList,task,target);
        }
    }

    public synchronized void handleFinish(TargetRunStatus status, Target target, List<Consumer<String>> consumersList,
                                          Task task){
        if (status == TargetRunStatus.FAILURE){
            handleFail(target,consumersList,task);
        }else{
            handleSucceed(target,consumersList,task);
        }
    }

    public abstract void handleFail(Target target, List<Consumer<String>> consumersList, Task task);

    public abstract void handleSucceed(Target target, List<Consumer<String>> consumersList, Task task);

    protected abstract void addToPool(Task task);

    public synchronized void addToSkipped(Target target){
        int size = this.skipped.size();
        skipped.add(target);
        if(skipped.size() != size){
            synchronized (this.synchroObj){
                taskRuntimeDTO.getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.SKIPPED);
                taskRuntimeDTO.upFinish();
                taskRuntimeDTO.getTargetByName(target.getName()).setFinishStatus(TargetRunStatus.SKIPPED);
            }
            System.out.println("up counter from:" + counter + " because target:"+ target.getName());
            upCounter();
        }
    }

    public synchronized void addToFailed(Target target){
        failed.add(target);
    }

    public synchronized void addToSucceed(Target target){
        succeed.add(target);
    }

    public synchronized void addToWarnings(Target target){
        warnings.add(target);
    }

    public Set<Target> getSkipped() {
        return skipped;
    }

    public Set<Target> getFailed() {
        return failed;
    }

    public Set<Target> getSucceed() {
        return succeed;
    }

    public Set<Target> getWarnings() {
        return warnings;
    }

    public synchronized void upCounter(){
        this.counter+= 1;
        if(counter==initSize){
            this.notifyAll();
        }
    }

    public TaskSummeryDTO getSummeryDTO() {
        return summeryDTO;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public TaskRuntimeDTO getTaskRuntimeDTO(){
        synchronized (this.synchroObj){
            return this.taskRuntimeDTO;
        }
    }

    public void setIsPaused(boolean paused){
        synchronized (this.isPausedObj){
            this.isPaused = paused;
            if(!paused){
                this.isPausedObj.notifyAll();
            }
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public Boolean getPausedObj() {
        return isPausedObj;
    }
}
