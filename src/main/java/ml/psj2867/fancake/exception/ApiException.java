package ml.psj2867.fancake.exception;

import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.util.MessageDto;

@Getter
public class ApiException extends RuntimeException{
    private static final long serialVersionUID = -3982470902307575402L;

    @Setter
    private MessageDto messageDto;

    public ApiException() {super();}    
    public ApiException(java.lang.String message) {super(message);}
    public ApiException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public ApiException(java.lang.Throwable cause) {super(cause);}
}