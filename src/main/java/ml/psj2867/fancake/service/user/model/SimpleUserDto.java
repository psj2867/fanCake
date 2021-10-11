package ml.psj2867.fancake.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.UnAuthorizedException;
import ml.psj2867.fancake.util.SecurityUtil;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {

    private String name;
    private Integer userIdx;
    private boolean isAuthenticate;

    public static SimpleUserDto current(){
        if(!SecurityUtil.isAuth())
            throw new UnAuthorizedException();
        return SimpleUserDto.builder()
                            .name(SecurityUtil.getName().get())
                            .userIdx(SecurityUtil.getUserIdx().get())
                            .isAuthenticate(true)
                            .build();
    }

    public static SimpleUserDto of(UserEntity userEntity) {
        return SimpleUserDto.builder()
                        .name(userEntity.getName())
                        .userIdx(userEntity.getIdx())
                        .isAuthenticate(true)  
                        .build();

    }
}