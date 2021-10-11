package ml.psj2867.demo.service.user.model.sign;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.demo.entity.UserEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SignUpUserForm{
    
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
                        .password(this.password)
                        .build();
    }
   
}