package ml.psj2867.fancake.service.channel.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.service.youtube.YoutubeService;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class ChannelDetailDto extends ChannelDto{

    private List<ChannelTopicDto> topics;
    
    public static ChannelDetailDto of(ChannelEntity channelEntity){
        return ChannelDetailDto.builder()
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