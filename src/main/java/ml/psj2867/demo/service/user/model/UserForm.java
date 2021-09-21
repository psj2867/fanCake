package ml.psj2867.demo.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ml.psj2867.demo.entity.UserEntity;

@Builder
@AllArgsConstructor
@Data
public class UserForm {
    
    private String id;
    private String password;

    public UserEntity convertToEntity(){
        UserEntity user = new UserEntity();
        user.setId(this.id);
        user.setPasswd(this.password);
        return user;
    }
    public static UserForm convert(UserEntity userEntity){
        UserForm user = new UserForm(userEntity.getId(), userEntity.getPasswd());
        return user;

    }

}