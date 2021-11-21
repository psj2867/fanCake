package ml.psj2867.fancake.service.comment.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import ml.psj2867.fancake.entity.CommentEntity;
import ml.psj2867.fancake.service.user.model.SimpleUserDto;

@Data
@Builder
public class CommentDto {
    public int contentIdx;
    public String content;
    public SimpleUserDto user;
    public LocalDateTime createdDate;
    
    public static CommentDto of(CommentEntity comment){
        return CommentDto.builder()
            .content(comment.getContent())
            .createdDate(comment.getCreatedDate())
            .user(SimpleUserDto.of(comment.getUser()))
            .contentIdx(comment.getIdx())
            .build();
    }
}
