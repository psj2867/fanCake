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
public class UpdateUserPasswordForm {
    
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;

    public void overWrite(UserEntity user){
        user.setPassword(this.newPassword);
    }

}