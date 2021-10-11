package ml.psj2867.demo.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends ApiException {
    private static final long serialVersionUID = -3982470902307575402L;

    private Object rejectedValue;
    private String resource;

    public NotFoundException(String resource, Object rejectedValue){
        super();
        this.rejectedValue = rejectedValue;
        this.resource = resource;           
    }
    public NotFoundException(String resource, Object rejectedValue, java.lang.Throwable cause){
        super(cause);
        this.rejectedValue = rejectedValue;
        this.resource = resource;           
    }
    
    public static NotFoundException of(String resource, Object rejectedValue){
        return new NotFoundException(resource, rejectedValue);
    }
}