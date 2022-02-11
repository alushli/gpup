package logic.workerEngine;

import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.Map;

public class WorkerEngine {
    private String name;
    private int numOfThreads;
    private int credits;
    private Map<String, RegisteredExecution> registeredExecutionMap = new HashMap<>();
    private TaskManager taskManager;

    public WorkerEngine(String name, int numOfThreads){
        this.name = name;
        this.numOfThreads = numOfThreads;
        this.credits = 0;
        this.taskManager = new TaskManager(this);
        this.taskManager.start();
    }

    public void subscribeTask(String taskName){
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
                    registeredExecutionMap.put(registerTaskDTO.getTaskName(), new RegisteredExecution(registerTaskDTO));
                }else{
                    System.out.println("Something went wrong");
                }
            }
        });
    }

    public void unsubscribeTask(String taskName){
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
                if(response.code() == 200){
                    registeredExecutionMap.remove(taskName);
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

}
