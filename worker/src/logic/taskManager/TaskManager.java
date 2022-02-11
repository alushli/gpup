package logic.taskManager;

import logic.workerEngine.WorkerEngine;
import okhttp3.HttpUrl;
import okhttp3.Response;
import utils.Constants;
import utils.HttpClientUtil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private WorkerEngine workerEngine;
    private ThreadPoolExecutor threadPoolExecutor;
    private int numOfThreads;
    TargetsDetails targetsDetails;


    public TaskManager(WorkerEngine workerEngine){
        this.workerEngine = workerEngine;
        this.numOfThreads = workerEngine.getNumOfThreads();
        this.threadPoolExecutor = new ThreadPoolExecutor(this.numOfThreads, this.numOfThreads, 60, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
        this.targetsDetails = new TargetsDetails();
    }

    public void start(){
        new Thread(()->{
            while (true){
                try{
                    Thread.sleep(500);
                }catch (Exception e){}

                if(this.workerEngine.getRegisteredExecutionMap().size()>0){

                    //handle targets run
                    if(numOfThreads - this.threadPoolExecutor.getActiveCount() > 0){
                        String finalUrl = HttpUrl.parse(Constants.GET_TARGET_TO_RUN).newBuilder().
                                addQueryParameter("targetAmount", String.valueOf(numOfThreads - this.threadPoolExecutor.getActiveCount()))
                                .build().
                                toString();

                        Response response = HttpClientUtil.runSync(finalUrl);
                        try{
                            if (response != null && response.code() == 200) {
                                //go over targets returned and send it to run
                                //update targetDetails
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
                            //go over tasks returned and update my tasks in worker engine
                        } else {
                            throw new Exception();
                        }
                    }catch (Exception e){
                        System.out.println("error! message: " + e.getMessage());
                    }

                }
            }
        }).start();
    }
}
