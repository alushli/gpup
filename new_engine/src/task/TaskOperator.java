package task;

import User.Admin;
import User.Worker;
import graph.Graph;
import newEnums.*;
import target.Target;

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
    protected Map<String, TargetAllStatuses> targetStatusMap;
    protected TaskStatus taskStatus;
    protected int numOfTargetForFinish;

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public synchronized void handleTargetFinish(String targetName, TargetAllStatuses runStatus){
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

    public synchronized void setStart(){
        this.taskStatus = TaskStatus.IN_PROCESS;
    }

}
