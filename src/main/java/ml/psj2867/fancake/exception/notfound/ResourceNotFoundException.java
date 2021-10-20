package ml.psj2867.fancake.exception.notfound;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends NotFoundException {
    private static final long serialVersionUID = -3982470902307575402L;

    private Object rejectedValue;
    private String resource;

    public ResourceNotFoundException(String resource, Object rejectedValue){
        super();
        this.rejectedValue = rejectedValue;
        this.resource = resource;           
    }
    public ResourceNotFoundException(String resource, Object rejectedValue, java.lang.Throwable cause){
        super(cause);
        this.rejectedValue = rejectedValue;
        this.resource = resource;           
    }
    
    public static ResourceNotFoundException of(String resource, Object rejectedValue){
        return new ResourceNotFoundException(resource, rejectedValue);
    }
}