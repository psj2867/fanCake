package ml.psj2867.fancake.exception.notfound;

import lombok.Getter;
import ml.psj2867.fancake.exception.ApiException;

@Getter
public class NotFoundException extends ApiException{
    private static final long serialVersionUID = -3982470902307575402L;

    public NotFoundException() {super();}    
    public NotFoundException(java.lang.String message) {super(message);}
    public NotFoundException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public NotFoundException(java.lang.Throwable cause) {super(cause);}
}