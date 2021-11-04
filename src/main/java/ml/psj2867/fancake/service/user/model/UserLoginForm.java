package ml.psj2867.fancake.service.user.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.entity.UserEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginForm{
    
    @NotBlank
    private String id;
    @NotBlank
    private String password;

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setId(this.id);
        user.setPassword(this.password);
        return user;
    }
}