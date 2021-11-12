package exceptions;

public class GeneralException extends Exception{
    protected String message;

    /* the function return the error information */
    public String errorInfo() {
        return "General error - ";
    }

    /* the function create new general exception */
    public GeneralException(String message){
      this.message = message;
    }

    /* the function return the error message */
	public String getMessage() { return message; }
}
