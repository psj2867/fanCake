package ml.psj2867.fancake.exception.bad;

import lombok.Getter;
import ml.psj2867.fancake.exception.ApiException;

@Getter
public class BadRequesetException extends ApiException{
    private static final long serialVersionUID = -3982470902307575402L;


    public BadRequesetException() {super();}    
    public BadRequesetException(java.lang.String message) {super(message);}
    public BadRequesetException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public BadRequesetException(java.lang.Throwable cause) {super(cause);}
}