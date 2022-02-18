package components.generalComponents.taskTable;

import com.google.gson.Gson;
import dtoObjects.TaskDTO;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class WorkerTaskTableRefresher extends TimerTask {
    private Consumer<List<DashboardTaskFX>> usersListConsumer;
    private int requestNumber;
    private BooleanProperty shouldUpdate;

    public WorkerTaskTableRefresher(BooleanProperty shouldUpdate, Consumer<List<DashboardTaskFX>> usersListConsumer) {
        this.shouldUpdate = shouldUpdate;
        this.usersListConsumer = usersListConsumer;
        requestNumber = 0;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        final int finalRequestNumber = ++requestNumber;
        HttpClientUtil.runAsync(Constants.TASKS_LIST, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on refresh tasks list.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfGraphs = response.body().string();
                Gson gson = new Gson();
                TaskDTO[] taskDTOS = gson.fromJson(jsonArrayOfGraphs, TaskDTO[].class);
                List<DashboardTaskFX> dashboardTaskFxes = new ArrayList<>();
                for (TaskDTO taskDTO:taskDTOS){
                    dashboardTaskFxes.add(new DashboardTaskFX(taskDTO));
                }
                usersListConsumer.accept(dashboardTaskFxes);
            }
        });
    }

}
