package ml.psj2867.fancake.service.video.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ml.psj2867.fancake.entity.VideoEntity;

@Builder
@AllArgsConstructor
@Data
public class VideoForm {
    
    private String videoId;


    public VideoEntity toEntity(){
        return VideoEntity.builder()
                            .videoId(this.videoId)
                            .build();
    }
}