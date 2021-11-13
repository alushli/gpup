package exceptions;

public class MenuOptionException extends GeneralException{
    /* the function return the error information */
    public String errorInfo(){
        return "Error in menu - ";
    }

    /* the function create new xml exception */
    public MenuOptionException(String message){
        super(message);
    }
}
