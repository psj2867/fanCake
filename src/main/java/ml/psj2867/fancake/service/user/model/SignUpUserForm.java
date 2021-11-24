package ml.psj2867.fancake.service.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.security.model.WithEncodedPassword;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SignUpUserForm implements WithEncodedPassword{
    
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String id;
    @NotBlank
    private String password;
    
    public UserEntity toEntity(){
        return UserEntity.builder()
                        .name(this.name)
                        .id(this.id)
                        .email(this.id)
                        .password( this.password )                      
                        .build();
    }
    @Override
    public void encode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
   
}