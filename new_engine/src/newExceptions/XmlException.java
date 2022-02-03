package newExceptions;

public class XmlException extends GeneralException {
    /* the function return the error information */
    public String errorInfo(){
        return "Error in file handling - ";
    }

    /* the function create new xml exception */
    public XmlException(String message){
        super(message);
    }
}
