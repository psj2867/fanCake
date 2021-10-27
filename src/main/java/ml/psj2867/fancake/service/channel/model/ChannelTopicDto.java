package ml.psj2867.fancake.service.channel.model;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.entity.ChannelTopicEntity;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChannelTopicDto{

    private String topic;
    
    public static ChannelTopicDto of(ChannelTopicEntity channelTopicEntity){
        return ChannelTopicDto.builder()
                        .topic(channelTopicEntity.getTopic())
                        .build();
    }
    public static List<ChannelTopicDto> of(ChannelEntity channelEntity){
        return channelEntity.getTopics().stream()
                            .map(ChannelTopicDto::of)
                            .collect(Collectors.toList());
    }
}