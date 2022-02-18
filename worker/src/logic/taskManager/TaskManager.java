package logic.taskManager;

import com.google.gson.Gson;
import components.generalComponents.targetsTask.TargetsTaskFX;
import dtoObjects.TargetToRunWorker;
import dtoObjects.WorkerTaskDTO;
import logic.job.CompilationJob;
import logic.job.SimulationJob;
import logic.registeredExecution.RegisteredExecution;
import logic.workerEngine.WorkerEngine;
import okhttp3.HttpUrl;
import okhttp3.Response;
import utils.Constants;
import utils.HttpClientUtil;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private WorkerEngine workerEngine;
    private ThreadPoolExecutor threadPoolExecutor;
    private int numOfThreads;
    TargetsDetails targetsDetails;//all targets i performed / performing!


    public TaskManager(WorkerEngine workerEngine){
        this.workerEngine = workerEngine;
        this.numOfThreads = workerEngine.getNumOfThreads();
        this.threadPoolExecutor = new ThreadPoolExecutor(this.numOfThreads, this.numOfThreads, 60, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
        this.targetsDetails = new TargetsDetails();
    }

    public synchronized List<TargetsTaskFX> getAllTargets(){
        return targetsDetails.getAll();
    }

    public synchronized int getNumOfThreadsOc(){
        return this.threadPoolExecutor.getActiveCount();
    }

    public void start(){
        Gson gson = new Gson();
        new Thread(()->{
            while (true){
                try{
                    Thread.sleep(500);
                }catch (Exception e){}

                if(this.workerEngine.getRegisteredExecutionMap().size()>0){

                    //handle targets run
                    if(numOfThreads - this.threadPoolExecutor.getActiveCount() > 0){
                        String finalUrl = HttpUrl.parse(Constants.GET_TARGET_TO_RUN).newBuilder().
                                addQueryParameter("amount", String.valueOf(numOfThreads - this.threadPoolExecutor.getActiveCount()))
                                .build().
                                toString();

                        Response response = HttpClientUtil.runSync(finalUrl);
                        try{
                            if (response != null && response.code() == 200) {
                                String body = response.body().string();
                                TargetToRunWorker[] targets = gson.fromJson(body, TargetToRunWorker[].class);
                                for (TargetToRunWorker target : targets){
                                    this.workerEngine.addToCredits(target.getPrice());
                                    RegisteredExecution registeredExecution = this.workerEngine.getRegisteredExecutionMap().get(target.getTaskName());
                                    registeredExecution.addToDoneTargetsByMe();
                                    RunTimeTargetDetails runTimeTargetDetails = new RunTimeTargetDetails(target);
                                    targetsDetails.addToList(runTimeTargetDetails);
                                    if(target.getTaskType().equals("Simulation")){
                                        this.threadPoolExecutor.execute(new SimulationJob(runTimeTargetDetails,target.getTargetName(), target.getGeneralInfo(),
                                                this.workerEngine.getRegisteredExecutionMap().get(target.getTaskName())));
                                    }else{
                                        this.threadPoolExecutor.execute(new CompilationJob(runTimeTargetDetails,target.getTargetName(), target.getGeneralInfo(),
                                                this.workerEngine.getRegisteredExecutionMap().get(target.getTaskName())));
                                    }
                                }

                            } else {
                                throw new Exception();
                            }
                        }catch (Exception e){
                            System.out.println("error! message: " + e.getMessage());
                        }
                    }

                    //handle update tasks status!
                    String finalUrl2 = HttpUrl.parse(Constants.GET_MY_TASKS_STATUS).newBuilder()
                            .build().
                            toString();

                    Response response = HttpClientUtil.runSync(finalUrl2);
                    try{
                        if (response != null && response.code() == 200) {
                            String jsonRes = response.body().string();
                            WorkerTaskDTO[] workerTaskDTOS = gson.fromJson(jsonRes, WorkerTaskDTO[].class);
                            for (WorkerTaskDTO workerTaskDTO : workerTaskDTOS){
                                synchronized (this.workerEngine){
                                    RegisteredExecution registeredExecution =  this.workerEngine.getRegisteredExecutionMap().get(workerTaskDTO.getTaskName());
                                    if(registeredExecution != null){
                                        registeredExecution.setTaskStatus(workerTaskDTO.getStatus());
                                        registeredExecution.setWorkers(workerTaskDTO.getWorkers());
                                        registeredExecution.setDoneTargets(workerTaskDTO.getDoneTargets());
                                        registeredExecution.setTotalTargets(workerTaskDTO.getTotalTargets());
                                    }else {
                                        throw new Exception();
                                    }
                                }
                            }
                        } else {
                            throw new Exception();
                        }
                        response.body().close();
                    }catch (Exception e){
                        System.out.println("error! message: " + e.getMessage());
                    }

                }
            }
        }).start();
    }
}
