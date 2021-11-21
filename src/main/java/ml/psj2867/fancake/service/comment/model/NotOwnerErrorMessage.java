package ml.psj2867.fancake.service.comment.model;

import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.exception.forbidden.NotOwnerException;
import ml.psj2867.fancake.service.user.model.SimpleUserDto;
import ml.psj2867.fancake.util.MessageDto;

@Getter
@Setter
public class NotOwnerErrorMessage extends MessageDto {

    private String loginUserName;
    private int loginUserIdx;
    private String ownerUserMame;
    private int ownerUserIdx;
    

    public NotOwnerErrorMessage(String message) {
        super(message);
    }

    public static NotOwnerErrorMessage of(SimpleUserDto loginUser, SimpleUserDto owner){
        NotOwnerErrorMessage dto = new NotOwnerErrorMessage(NotOwnerException.DEFAULT_MSG);
        dto.setLoginUserIdx(loginUser.getUserIdx());
        dto.setLoginUserName(loginUser.getName());
        dto.setOwnerUserMame(owner.getName());
        dto.setOwnerUserIdx(owner.getUserIdx());
        return dto;
    }
    
}
