package exceptions;

public class TaskException extends GeneralException {
    private static String createFolderError = "can't create new folder";

    /* the function return xml load error */
    public static String getCreateFolderError() {
        return createFolderError;
    }

    /* the function return the error information */
    public String errorInfo(){
        return "Error in task - ";
    }

    /* the function create new xml exception */
    public TaskException(String message){
        super(message);
    }
}
