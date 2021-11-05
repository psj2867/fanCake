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
        UserEntity user = UserEntity.builder()
                                    .id(this.id)
                                    .name(this.name)
                                    .email(this.email)
                                    .phoneNumber(this.mobile)
                                    .loginType(LoginTypeEnum.NAVER)
                                    .build();
        return user;
    }
}