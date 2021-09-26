package ml.psj2867.demo.service.channel.model;

import lombok.Builder;
import lombok.Data;
import ml.psj2867.demo.entity.ChannelEntity;

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