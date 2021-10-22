package ml.psj2867.fancake.service.channel.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.service.api.model.ListForm;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ChannesVideoslListForm extends ListForm<VideoEntity> { 

    @Nullable
    private String videoTitle;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("videoTitle","stockSize","pricePerShare","createdDate","expirationDate");
    }

    public Specification<VideoEntity> toSpec(ChannelEntity channel){
        this.and(this.equl(channel, "channel"));
        if(StringUtils.hasLength(this.videoTitle))
            this.and( this.like(this.videoTitle, "videoTitle"));
        return this.getSpec();
    }
}