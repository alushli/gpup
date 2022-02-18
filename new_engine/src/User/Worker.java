package User;

import dtoObjects.TargetToRunWorker;
import dtoObjects.WorkerTaskDTO;
import target.Target;
import task.TaskOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker extends User{
    private int numOfThreads;
    private int threadInUse;
    private Map<String,TaskOperator> taskMap = new HashMap<>();
    private Map<String, TaskOperator> activeTasks = new HashMap<>();

    public Worker(String name, int numOfThreads){
        this.name = name;
        this.numOfThreads = numOfThreads;
        this.threadInUse = 0;
        this.isActive = true;
    }

    public synchronized void addToTasks(TaskOperator taskOperator){
        this.taskMap.put(taskOperator.getName(), taskOperator);
        this.activeTasks.put(taskOperator.getName(), taskOperator);
    }

    public synchronized void removeTask(TaskOperator taskOperator){
        this.taskMap.remove(taskOperator.getName());
        this.activeTasks.remove(taskOperator.getName());
    }

    public synchronized void makePause(TaskOperator taskOperator){
        this.activeTasks.remove(taskOperator.getName());
    }
    public synchronized void makeStart(TaskOperator taskOperator){
        this.activeTasks.put(taskOperator.getName(), taskOperator);
    }

    public synchronized List<WorkerTaskDTO> getAllTasks(){
        List<WorkerTaskDTO> tasks = new ArrayList<>();
        for (Map.Entry<String,TaskOperator> entry : this.taskMap.entrySet()){
            tasks.add(new WorkerTaskDTO(entry.getValue()));
        }
        return tasks;
    }

    public synchronized List<TargetToRunWorker> getTargetsToRun(int amount){
        List<TargetToRunWorker> targets = new ArrayList<>();
        int count = 0;
        for (int i = 0; i<amount && count<amount; i++){
            for (Map.Entry<String, TaskOperator> entry : this.activeTasks.entrySet()){
                Target target = entry.getValue().getTargetToRun();
                if(target != null){
                    count++;
                    targets.add(new TargetToRunWorker(target, entry.getValue()));
                    entry.getValue().updateTargetWorker(target.getName(), this.name);
                    if(count==amount){
                        return targets;
                    }
                }
            }
        }
        return targets;
    }

}
