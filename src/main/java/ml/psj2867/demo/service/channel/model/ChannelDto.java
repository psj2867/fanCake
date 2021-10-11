package ml.psj2867.demo.service.channel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.demo.entity.ChannelEntity;
import ml.psj2867.demo.service.youtube.YoutubeService;
import ml.psj2867.demo.service.youtube.modoel.YoutubeThumbnailEnum;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChannelDto{

    private String channelId;
    private String channelTitle;
    private String thumbnailUrl;
    private String channelUrl;
    
    public static ChannelDto of(ChannelEntity channelEntity){
        return ChannelDto.builder()
                        .channelId(channelEntity.getChannnelId())
                        .channelTitle(channelEntity.getChannelTitle())
                        .thumbnailUrl(getThumbnailUrl(channelEntity.getChannnelId()))
                        .channelUrl(getChannelUrl(channelEntity.getChannnelId()))
                        .build();
    }
    private static String getThumbnailUrl(String channelId){
        return YoutubeThumbnailEnum.default_size.getUrl(channelId);
    }
    private static String getChannelUrl(String channelId){
        return YoutubeService.getChannelUrl(channelId);
    }
}