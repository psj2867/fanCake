package ml.psj2867.fancake.service.channel.model;

import lombok.Builder;
import lombok.Data;
import ml.psj2867.fancake.entity.ChannelEntity;

@Builder
@Data
public class ChannelForm{
    private String channel_id;

    public ChannelEntity toEntity(){
        ChannelEntity channel = ChannelEntity.builder()
                                                .channnelId(this.channel_id)
                                                .build();
        return channel;
    }
}