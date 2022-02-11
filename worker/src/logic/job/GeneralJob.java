package logic.job;

import logic.taskManager.RunTimeTargetDetails;
import logic.taskManager.TargetsDetails;
import logic.utils.LogsManager;
import newEnums.TargetRunStatus;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        this.logsManager = new LogsManager(targetName);
        this.executionName = executionName;
    }

    @Override
    public void run() {
        specificJob();

        //this.runTimeTargetsDetails.updateTargetStatusToFinishRT(this.targetName, this.executionName, this.runResult);

        updateServerOnRunResult();
    }
    public void updateServerOnRunResult() {
//        RunningResultDetails runningResultDetails = new RunningResultDetails(this.targetName, this.executionName, this.logsManager.getLogs(), this.runResult);
//
//        String runningResultDetailsJson = GsonConfig.gson.toJson(runningResultDetails);
//        String body = "runningResultDetails=" + runningResultDetailsJson;
//        Request request = new Request.Builder().url(HttpConfig.BASE_URL + "/target-finished")
//                .post(RequestBody.create(body.getBytes()))
//                .build();
//
//        Call call = HttpConfig.HTTP_CLIENT.newCall(request);
//
//        try {
//            final Response response = call.execute();
//            if (response.code() != 200) {
//                throw new Exception(response.message());
//            }
//        } catch (Exception e) {
//            System.out.println("error! message: " + e.getMessage());
//        }
    }

    public abstract void specificJob();
}
