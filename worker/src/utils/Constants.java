package utils;

public class Constants {
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_app_war_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String WORKER_LOGIN = FULL_SERVER_PATH + "/worker-login";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/get-all-users";
    public final static String TASKS_LIST = FULL_SERVER_PATH + "/get-all-tasks";

}
