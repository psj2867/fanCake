package ml.psj2867.fancake.service.channel.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.service.api.model.ListForm;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ChannelListForm extends ListForm<ChannelEntity> { 

    private String channelTitle;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("channelTitle","createdDate");
    }

    public Specification<ChannelEntity> toSpec(){
        if(StringUtils.hasLength(this.channelTitle))
            this.and( this.like(this.channelTitle, "channelTitle"));
        return this.getSpec();
    }
}