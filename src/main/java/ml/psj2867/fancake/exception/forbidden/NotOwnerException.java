package ml.psj2867.fancake.exception.forbidden;

import lombok.Getter;
import ml.psj2867.fancake.util.MessageDto;

@Getter
public class NotOwnerException extends ForbiddenException {
    public final static String DEFAULT_MSG = "Not the owner";

    public NotOwnerException(MessageDto messageDto) {super();this.setMessageDto(messageDto);}    
    public NotOwnerException(java.lang.String message) {super(message);}
    public NotOwnerException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public NotOwnerException(java.lang.Throwable cause) {super(cause);}
}