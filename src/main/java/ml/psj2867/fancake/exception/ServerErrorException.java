package ml.psj2867.fancake.exception;


public class ServerErrorException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ServerErrorException() {
    }
    
    public ServerErrorException(java.lang.String message) {
        super(message);
    }
    
    public ServerErrorException(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }
    
    public ServerErrorException(java.lang.Throwable cause) {
        super(cause);
    }
    
}