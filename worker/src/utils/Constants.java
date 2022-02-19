package utils;

public class Constants {
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_app_war";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String WORKER_LOGIN = FULL_SERVER_PATH + "/worker-login";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/get-all-users";
    public final static String TASKS_LIST = FULL_SERVER_PATH + "/get-all-tasks";
    public final static String TASK_SUBSCRIBE = FULL_SERVER_PATH + "/task-subscribe";
    public final static String TASK_UNSUBSCRIBE = FULL_SERVER_PATH + "/task-unsubscribe";
    public final static String PAUSE_TASK = FULL_SERVER_PATH + "/task-pause";
    public final static String GET_MY_TASKS_STATUS = FULL_SERVER_PATH + "/get-all-subscribe-tasks";
    public final static String GET_TARGET_TO_RUN = FULL_SERVER_PATH + "/get-target-to-run";
    //to-do!
    public final static String UPDATE_TARGET = FULL_SERVER_PATH + "/update-target-run";



}
