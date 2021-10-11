package ml.psj2867.demo.exception;


public class LoginException extends RuntimeException{

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