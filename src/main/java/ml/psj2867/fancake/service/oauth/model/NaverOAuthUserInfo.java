package ml.psj2867.fancake.service.oauth.model;

import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

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
    @NotBlank
    private String id;
    private String email;
    private String mobile;
    private String mobile_e164;
    private String name;    

    public UserEntity convertToEntity(){
        if(! StringUtils.hasLength( this.name ))
            this.name = this.id;
        if(! StringUtils.hasLength( this.email ))
            this.email = "";
        
        UserEntity user = UserEntity.builder()
                                    .id(this.id)
                                    .name(this.name)
                                    .email(this.email)
                                    .phoneNumber(this.mobile)
                                    .loginType(LoginTypeEnum.NAVER)
                                    .password("")
                                    .build();
        return user;
    }
}