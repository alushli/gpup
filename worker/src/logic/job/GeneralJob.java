package logic.job;

import com.google.gson.Gson;
import dtoObjects.TargetUpdate;
import logic.taskManager.RunTimeTargetDetails;
import logic.taskManager.TargetsDetails;
import logic.utils.LogsManager;
import newEnums.TargetRunStatus;
import okhttp3.*;
import utils.Constants;
import utils.HttpClientUtil;

public abstract class GeneralJob implements Runnable {
    protected String targetName;
    protected LogsManager logsManager;
    protected TargetRunStatus runResult;
    protected String generalInfo;
    protected String executionName;
    protected RunTimeTargetDetails runTimeTargetDetails;


    public GeneralJob(RunTimeTargetDetails runTimeTargetDetails, String targetName, String generalInfo, String executionName) {
        this.runTimeTargetDetails = runTimeTargetDetails;
        this.targetName = targetName;
        this.generalInfo = generalInfo;
        this.logsManager = runTimeTargetDetails.getLogsManager();
        this.executionName = executionName;
    }

    @Override
    public void run() {
        specificJob();
        this.runTimeTargetDetails.setRunStatus(this.runResult.toString());
        updateServerOnRunResult();
    }
    public void updateServerOnRunResult() {
        Gson gson = new Gson();
        TargetUpdate targetUpdate = new TargetUpdate(targetName, executionName, runResult.toString(), runTimeTargetDetails.getLogsManager().getLogs());
        String body = "target=" + gson.toJson(targetUpdate);

        String finalUrl = HttpUrl.parse(Constants.UPDATE_TARGET).newBuilder()
                .build().
                toString();

        Request request = new Request.Builder().url(finalUrl)
                .post(RequestBody.create(body.getBytes()))
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);

        try{
            Response response = call.execute();
            if(response.code() == 200){
                System.out.println("Target update!");
            }else{
                System.out.println("Target did not update!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public abstract void specificJob();
}
