package logic.taskManager;

import logic.workerEngine.WorkerEngine;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private WorkerEngine workerEngine;
    private ThreadPoolExecutor threadPoolExecutor;
    private int numOfThreads;


    public TaskManager(WorkerEngine workerEngine){
        this.workerEngine = workerEngine;
        this.numOfThreads = workerEngine.getNumOfThreads();
        this.threadPoolExecutor = new ThreadPoolExecutor(this.numOfThreads, this.numOfThreads, 60, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }

    public void start(){
        new Thread(()->{
            while (true){
                try{
                    Thread.sleep(500);
                }catch (Exception e){}

                if(this.workerEngine.getRegisteredExecutionMap().size()>0){
                    if(numOfThreads - this.threadPoolExecutor.getActiveCount() > 0){
                        //request to server to ask for target to run.
                        //run on the targets from response
                        //request from server for stop/unstop tasks
                        //update my registered tasks
                    }
                }

            }
        }).start();
    }
}
