package ml.psj2867.fancake.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private static final long serialVersionUID = -3982470902307575402L;


    public ApiException() {super();}    
    public ApiException(java.lang.String message) {super(message);}
    public ApiException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public ApiException(java.lang.Throwable cause) {super(cause);}
}