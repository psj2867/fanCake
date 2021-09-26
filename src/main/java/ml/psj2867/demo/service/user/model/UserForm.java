package ml.psj2867.demo.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ml.psj2867.demo.entity.UserEntity;

@Builder
@Data
public class UserForm {
    
    private Integer userIdx;
    private String id;
    private String password;
    @Builder.Default
    private boolean isCreator = false;

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setId(this.id);
        user.setPasswd(this.password);
        user.setCreator(this.isCreator);
        return user;
    }
    public static UserForm of(UserEntity userEntity){
        UserForm user = UserForm.builder()
                            .userIdx(userEntity.getIdx())
                            .id(userEntity.getId())
                            .password(userEntity.getPasswd())
                            .build();
        return user;

    }

}