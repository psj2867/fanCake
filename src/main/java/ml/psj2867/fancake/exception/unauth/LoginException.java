package ml.psj2867.fancake.exception.unauth;

import ml.psj2867.fancake.exception.ApiException;

public class LoginException extends ApiException{

    private static final long serialVersionUID = 1L;

    public LoginException() {
    }
    
    public LoginException(java.lang.String message) {
        super(message);
    }
    
    public LoginException(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }
    
    public LoginException(java.lang.Throwable cause) {
        super(cause);
    }
    
}