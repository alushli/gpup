package utils;

import okhttp3.OkHttpClient;

public class Constants {
    public final static String BASE_DOMAIN = "localhost";
    public final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_app_war_exploded";
    public final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String ADMIN_LOGIN = FULL_SERVER_PATH + "/admin-login";
    public final static String GRAPH_TARGETS = FULL_SERVER_PATH + "/graph-targets";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/get-all-users";
}
