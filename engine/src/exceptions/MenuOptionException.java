package exceptions;

public class MenuOptionException extends GeneralException{
    private static String xmlLoadError = "You need to load xml file first";

    /* the function return xml load error */
    public static String getXmlLoadError() {
        return xmlLoadError;
    }

    /* the function return the error information */
    public String errorInfo(){
        return "Error in menu - ";
    }

    /* the function create new xml exception */
    public MenuOptionException(String message){
        super(message);
    }
}
