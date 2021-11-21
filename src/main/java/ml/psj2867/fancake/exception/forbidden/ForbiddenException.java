package ml.psj2867.fancake.exception.forbidden;

import lombok.Getter;
import ml.psj2867.fancake.exception.ApiException;

@Getter
public class ForbiddenException extends ApiException{
    private static final long serialVersionUID = -3982470902307575402L;

    public ForbiddenException() {super();}    
    public ForbiddenException(java.lang.String message) {super(message);}
    public ForbiddenException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public ForbiddenException(java.lang.Throwable cause) {super(cause);}
}