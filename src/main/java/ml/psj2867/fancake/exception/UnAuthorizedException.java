package ml.psj2867.fancake.exception;


public class UnAuthorizedException extends LoginException{

    private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
    }
    public UnAuthorizedException(java.lang.Throwable cause) {
        super(cause);
    }
    
}