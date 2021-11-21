package ml.psj2867.fancake.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.unauth.UnAuthorizedException;
import ml.psj2867.fancake.util.SecurityUtil;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {

    private Integer userIdx;
    private String name;
    private String userID;

    public static SimpleUserDto current() throws UnAuthorizedException{
        if(!SecurityUtil.isAuth())
            throw new UnAuthorizedException();
        return SimpleUserDto.builder()
                            .userIdx(SecurityUtil.getUserIdx().get())
                            .name(SecurityUtil.getName().get())
                            .build();
    }

    public static SimpleUserDto of(UserEntity userEntity) {
        return SimpleUserDto.builder()
                        .userIdx(userEntity.getIdx())
                        .name(userEntity.getName())
                        .userID(userEntity.getId())
                        .build();

    }
}