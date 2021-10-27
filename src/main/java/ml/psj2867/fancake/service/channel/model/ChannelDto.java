package ml.psj2867.fancake.service.channel.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.service.youtube.YoutubeService;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChannelDto{

    private int channelIdx;
    private String channelId;
    private String channelTitle;
    private String thumbnailUrl;
    private String channelUrl;
    private List<ChannelTopicDto> topics;
    
    public static ChannelDto of(ChannelEntity channelEntity){
        return ChannelDto.builder()
                        .channelIdx(channelEntity.getIdx())
                        .channelId(channelEntity.getChannnelId())
                        .channelTitle(channelEntity.getChannelTitle())
                        .thumbnailUrl(channelEntity.getThumbnailUrl())
                        .channelUrl(getChannelUrl(channelEntity.getChannnelId()))
                        .topics(ChannelTopicDto.of(channelEntity))
                        .build();
    }
    private static String getChannelUrl(String channelId){
        return YoutubeService.getChannelUrl(channelId);
    }
}