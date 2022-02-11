package components.generalComponents.usersTable;

import com.google.gson.Gson;
import dtoObjects.UserDTO;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.Constants;
import utils.HttpClientUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class UserListRefresher extends TimerTask {

    private  Consumer<List<UserDTO>> usersListConsumer;
    private int requestNumber;
    private  BooleanProperty shouldUpdate;


    public UserListRefresher(BooleanProperty shouldUpdate, Consumer<List<UserDTO>> usersListConsumer) {
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
        HttpClientUtil.runAsync(Constants.USERS_LIST, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Fail on refresh users list.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfUsersNames = response.body().string();
                Gson gson = new Gson();
                UserDTO[] usersNames = gson.fromJson(jsonArrayOfUsersNames, UserDTO[].class);
                usersListConsumer.accept(Arrays.asList(usersNames));
            }
        });
    }
}
