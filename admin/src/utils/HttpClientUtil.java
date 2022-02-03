package utils;

import okhttp3.*;

import java.io.IOException;

public class HttpClientUtil {
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public static void runAsync(String finalUrl, Callback callback) {
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);

        call.enqueue(callback);
    }

    public static Response runSync(String finalUrl) {
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            return response;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
