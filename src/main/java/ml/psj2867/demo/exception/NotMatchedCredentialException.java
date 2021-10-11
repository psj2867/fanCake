package ml.psj2867.demo.exception;


public class NotMatchedCredentialException extends LoginException{

    private static final long serialVersionUID = 1L;

    public NotMatchedCredentialException() {
    }
    
    public NotMatchedCredentialException(java.lang.String message) {
        super(message);
    }
    
    public NotMatchedCredentialException(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }
    
    public NotMatchedCredentialException(java.lang.Throwable cause) {
        super(cause);
    }
    
}