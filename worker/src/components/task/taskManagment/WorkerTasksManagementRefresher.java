package components.task.taskManagment;

import com.google.gson.Gson;
import components.generalComponents.targetsTask.TargetsTaskFX;
import components.generalComponents.workerTasks.WorkerTasksFX;
import dtoObjects.TaskDTO;
import dtoObjects.WorkerTaskDTO;
import javafx.beans.property.BooleanProperty;
import logic.workerEngine.WorkerEngine;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class WorkerTasksManagementRefresher extends TimerTask {
    private Consumer<List<WorkerTasksFX>> taskConsumer;
    private Consumer<List<TargetsTaskFX>> targetConsumer;
    private Consumer<Integer> credits;
    private Consumer<Integer> ocThreads;
    private Consumer<Integer> sumThreads;
    private int requestNumber;
    private BooleanProperty shouldUpdate;
    private WorkerEngine workerEngine;
    private Consumer<LinkedHashMap<String, List<String>>> logs;

    public WorkerTasksManagementRefresher(BooleanProperty shouldUpdate, Consumer<List<WorkerTasksFX>> taskConsumer, WorkerEngine workerEngine, Consumer<List<TargetsTaskFX>> targetConsumer,
                                          Consumer<Integer> credits, Consumer<Integer> ocThreads, Consumer<Integer> sumThreads, Consumer<LinkedHashMap<String, List<String>>> logs) {
        this.shouldUpdate = shouldUpdate;
        this.taskConsumer = taskConsumer;
        requestNumber = 0;
        this.workerEngine = workerEngine;
        this.targetConsumer = targetConsumer;
        this.credits = credits;
        this.sumThreads = sumThreads;
        this.ocThreads = ocThreads;
        this.logs = logs;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        List<TargetsTaskFX> targetsTaskFXES= workerEngine.getAllMyTargets();
        final int finalRequestNumber = ++requestNumber;
        taskConsumer.accept(workerEngine.getAllTasksByMe());
        targetConsumer.accept(targetsTaskFXES);
        credits.accept(workerEngine.getCredits());
        ocThreads.accept(workerEngine.getOcThreads());
        sumThreads.accept(workerEngine.getSumThreads());
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        for (TargetsTaskFX targetFx : targetsTaskFXES){
            map.put(targetFx.getName(), targetFx.getLogs());
        }
        logs.accept(map);
    }
}
