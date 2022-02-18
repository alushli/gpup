package components.tasks.taskManagement;

import com.google.gson.Gson;
import components.generalComponents.graphsTable.GraphFx;
import dtoObjects.GraphDTO;
import dtoObjects.TargetRunFX;
import dtoObjects.TaskRuntimeForAdmin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class TaskManagmentRefresher  extends TimerTask {

    private Consumer<List<TargetRunFX>> targetConsumer;
    private int requestNumber;
    private BooleanProperty shouldUpdate;
    private String taskName;
    private IntegerProperty doneTargets;
    private Consumer<Integer> workers;
    private Consumer<String> status;
    private Consumer<Float> progress;
    private Consumer<Integer> allTargets;
    private Consumer<Integer> finishTarget;
    private Consumer<List<String>> logs;

    public TaskManagmentRefresher(BooleanProperty shouldUpdate, Consumer<List<TargetRunFX>> usersListConsumer, String taskName, IntegerProperty doneTargets, Consumer<Integer> workers,
                                  Consumer<String> status, Consumer<Float> progress, Consumer<Integer> allTargets,Consumer<Integer> finishTarget, Consumer<List<String>> logs) {
        this.shouldUpdate = shouldUpdate;
        this.targetConsumer = usersListConsumer;
        requestNumber = 0;
        this.taskName = taskName;
        this.doneTargets = doneTargets;
        this.workers = workers;
        this.status = status;
        this.progress = progress;
        this.finishTarget = finishTarget;
        this.allTargets = allTargets;
        this.logs = logs;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        final int finalRequestNumber = ++requestNumber;

        String finalUrl = HttpUrl.parse(Constants.GET_TASK_INFO).newBuilder().
                addQueryParameter("taskName", taskName)
                .build().
                toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on refresh targets list.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.code()==200){
                    String jsonArrayOfTask = response.body().string();
                    Gson gson = new Gson();
                    TaskRuntimeForAdmin taskRuntimeForAdmin = gson.fromJson(jsonArrayOfTask, TaskRuntimeForAdmin.class);
                    //update the tables!
                    targetConsumer.accept(taskRuntimeForAdmin.getTargets());
                    doneTargets.set(taskRuntimeForAdmin.getDoneTargets());
                    workers.accept(taskRuntimeForAdmin.getWorkers());
                    status.accept(taskRuntimeForAdmin.getTaskStatus());
                    progress.accept((float)taskRuntimeForAdmin.getDoneTargets()  / (float)taskRuntimeForAdmin.getTargets().size());
                    allTargets.accept(taskRuntimeForAdmin.getTotalTargets());
                    finishTarget.accept(taskRuntimeForAdmin.getDoneTargets());
                    logs.accept(taskRuntimeForAdmin.getLogs());
                }else{
                    System.out.println("Something went wrong!!!");
                }

            }
        });
    }
}
