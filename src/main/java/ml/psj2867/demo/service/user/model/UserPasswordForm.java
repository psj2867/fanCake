package ml.psj2867.demo.service.user.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.demo.entity.UserEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordForm {
    
    @NotBlank
    private String password;

    public void overWrite(UserEntity user){
        user.setPassword(this.password);
    }

}