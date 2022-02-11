package components.generalComponents.graphsTable;

import com.google.gson.Gson;
import dtoObjects.GraphDTO;
import dtoObjects.UserDTO;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class GraphListRefresher extends TimerTask {


    private Consumer<List<GraphFx>> usersListConsumer;
    private int requestNumber;
    private BooleanProperty shouldUpdate;

    public GraphListRefresher(BooleanProperty shouldUpdate, Consumer<List<GraphFx>> usersListConsumer) {
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
        HttpClientUtil.runAsync(Constants.GRAPH_LIST, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on refresh graphs list.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfGraphs = response.body().string();
                Gson gson = new Gson();
                GraphDTO[] graphDTOS = gson.fromJson(jsonArrayOfGraphs, GraphDTO[].class);
                List<GraphFx> graphFxes = new ArrayList<>();
                for (GraphDTO graphDTO:graphDTOS){
                    graphFxes.add(new GraphFx(graphDTO));
                }
                usersListConsumer.accept(graphFxes);
            }
        });
    }
}
