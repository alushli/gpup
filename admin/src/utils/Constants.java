package utils;

import okhttp3.OkHttpClient;

public class Constants {
    public final static String BASE_DOMAIN = "localhost";
    public final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_app_war";
    public final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String ADMIN_LOGIN = FULL_SERVER_PATH + "/admin-login";
    public final static String GRAPH_TARGETS = FULL_SERVER_PATH + "/graph-targets";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/get-all-users";
    public final static String GRAPH_LIST = FULL_SERVER_PATH + "/get-all-graphs";
    public final static String GRAPH_INFO = FULL_SERVER_PATH + "/general-info";
    public final static String TARGET_INFO = FULL_SERVER_PATH + "/target-info";
    public final static String FIND_CIRCLE = FULL_SERVER_PATH + "/find-circle";
    public final static String FIND_PATHS = FULL_SERVER_PATH + "/find-paths";
    public final static String TASKS_LIST = FULL_SERVER_PATH + "/get-all-tasks";
    public final static String TASKS_LIST_DONE = FULL_SERVER_PATH + "/get-all-done-tasks";
    public final static String CHECK_TASK_NAME = FULL_SERVER_PATH + "/check-task-name";
    public final static String CREATE_NEW_TASK = FULL_SERVER_PATH + "/create-task";
    public final static String CREATE_COPY_TASK = FULL_SERVER_PATH + "/create-task-copy";
    public final static String GET_TASK_INFO = FULL_SERVER_PATH + "/get-task-runtime";
    public final static String TASK_ACTION = FULL_SERVER_PATH + "/task-action";


}
