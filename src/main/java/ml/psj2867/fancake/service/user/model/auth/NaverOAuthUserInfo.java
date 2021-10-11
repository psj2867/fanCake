package ml.psj2867.fancake.service.user.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.psj2867.fancake.entity.UserEntity;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class NaverOAuthUserInfo{
    private String id;
    private String email;
    private String mobile;
    private String mobile_e164;
    private String name;    

    public UserEntity convertToEntity(){
        UserEntity user = new UserEntity();
        user.setId(this.id);
        user.setLoginType(LoginTypeEnum.NAVER);
        return user;
    }
}