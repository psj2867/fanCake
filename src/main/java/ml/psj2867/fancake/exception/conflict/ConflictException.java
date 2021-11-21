package ml.psj2867.fancake.exception.conflict;

import lombok.Getter;
import ml.psj2867.fancake.exception.ApiException;

@Getter
public class ConflictException extends ApiException {
    private static final long serialVersionUID = -3982470902307575402L;

    public ConflictException() {super();}    
    public ConflictException(java.lang.String message) {super(message);}
    public ConflictException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public ConflictException(java.lang.Throwable cause) {super(cause);}
}