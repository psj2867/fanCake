package ml.psj2867.fancake.service.channel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.service.youtube.YoutubeService;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChannelDto{

    private int channelIdx;
    private String channelId;
    private String channelTitle;
    private String thumbnailUrl;
    private String channelUrl;
    
    public static ChannelDto of(ChannelEntity channelEntity){
        return ChannelDto.builder()
                        .channelIdx(channelEntity.getIdx())
                        .channelId(channelEntity.getChannnelId())
                        .channelTitle(channelEntity.getChannelTitle())
                        .thumbnailUrl(channelEntity.getThumbnailUrl())
                        .channelUrl(getChannelUrl(channelEntity.getChannnelId()))
                        .build();
    }
    private static String getChannelUrl(String channelId){
        return YoutubeService.getChannelUrl(channelId);
    }
}