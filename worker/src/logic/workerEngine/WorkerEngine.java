package logic.workerEngine;

import com.google.gson.Gson;
import components.generalComponents.targetsTask.TargetsTaskFX;
import components.generalComponents.workerTasks.WorkerTasksFX;
import dtoObjects.RegisterTaskDTO;
import logic.registeredExecution.RegisteredExecution;
import logic.taskManager.TaskManager;
import newEnums.TaskStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.io.SyncFailedException;
import java.util.*;

public class WorkerEngine {
    private String name;
    private int numOfThreads;
    private int credits;
    private Map<String, RegisteredExecution> registeredExecutionMap = new HashMap<>();
    private TaskManager taskManager;
    private Set<String> pausedTasks = new HashSet<>();

    public WorkerEngine(String name, int numOfThreads){
        this.name = name;
        this.numOfThreads = numOfThreads;
        this.credits = 0;
        this.taskManager = new TaskManager(this);
        this.taskManager.start();
    }

    public void subscribeTask(String taskName){
        if(!registeredExecutionMap.containsKey(taskName)){
            String finalUrl = HttpUrl.parse(Constants.TASK_SUBSCRIBE).newBuilder().
                    addQueryParameter("taskName", taskName)
                    .build().
                    toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("Something went wrong");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.code() == 200){
                        Gson gson = new Gson();
                        RegisterTaskDTO registerTaskDTO = gson.fromJson(response.body().string(), RegisterTaskDTO.class);
                        synchronized (this){
                            registeredExecutionMap.put(registerTaskDTO.getTaskName(), new RegisteredExecution(registerTaskDTO));
                        }
                    }else{
                        System.out.println("Something went wrong");
                    }
                }
            });
        }
    }

    public void unsubscribeTask(String taskName){
        if(registeredExecutionMap.containsKey(taskName)) {
            String finalUrl = HttpUrl.parse(Constants.TASK_UNSUBSCRIBE).newBuilder().
                    addQueryParameter("taskName", taskName)
                    .build().
                    toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("Something went wrong");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.code() == 200) {
                        synchronized (this){
                            registeredExecutionMap.remove(taskName);
                            System.out.println(taskName + " removed!");
                        }
                    } else {
                        System.out.println("Something went wrong");
                    }
                }
            });
        }
    }

    public void pauseTask(String taskName, String action){
        String finalUrl = HttpUrl.parse(Constants.PAUSE_TASK).newBuilder().
                addQueryParameter("taskName", taskName)
                .addQueryParameter("action", action)
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Something went wrong");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.code() == 200){
                    System.out.println("Task paused!");
                }else{
                    System.out.println("Something went wrong");
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public int getNumOfThreads() {
        return numOfThreads;
    }

    public int getCredits() {
        return credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfThreads(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Map<String, RegisteredExecution> getRegisteredExecutionMap() {
        return registeredExecutionMap;
    }

    public synchronized List<WorkerTasksFX> getAllTasksByMe(){
        List<WorkerTasksFX> tasks = new ArrayList<>();
        for (Map.Entry<String, RegisteredExecution> entry : this.registeredExecutionMap.entrySet()){
            tasks.add(new WorkerTasksFX(entry.getValue()));
        }
        return tasks;
    }

    public synchronized List<TargetsTaskFX> getAllMyTargets(){
        return taskManager.getAllTargets();
    }

    public synchronized void addToCredits(int amount){
        this.credits += amount;
    }

    public synchronized int getOcThreads(){
        return this.taskManager.getNumOfThreadsOc();
    }

    public synchronized int getSumThreads(){
        return this.numOfThreads;
    }

}
