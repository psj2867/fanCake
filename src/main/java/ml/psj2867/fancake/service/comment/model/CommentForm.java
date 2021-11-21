package ml.psj2867.fancake.service.comment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.psj2867.fancake.entity.CommentEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.entity.VideoEntity;

@Data
@NoArgsConstructor
public class CommentForm {

    private String content;

    public CommentEntity toEntity(UserEntity user, VideoEntity video){
        return CommentEntity.builder()
                        .content(this.content)
                        .user(user)
                        .video(video)
                        .build();
    }
}
