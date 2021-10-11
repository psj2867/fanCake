package ml.psj2867.fancake.service.user.model;

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
public class IdDto {
    private String id;
    private Integer userIdx;

    public static IdDto of(UserEntity userEntity) {
        return IdDto.builder()
                        .id(userEntity.getId())
                        .userIdx(userEntity.getIdx())
                        .build();

    }
}