package ml.psj2867.fancake.service.user.model;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.security.model.WithEncodedPassword;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordForm implements WithEncodedPassword {

    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;

    public void overWrite(UserEntity user) {
        user.setPassword(this.newPassword);
    }

    @Override
    public void encode(PasswordEncoder passwordEncoder) {
        this.newPassword = passwordEncoder.encode(newPassword);
    }

}