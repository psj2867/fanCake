package ml.psj2867.demo.service.video.model;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.api.model.ListForm;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class VideoListForm extends ListForm<VideoEntity> { 

    @Nullable
    private String videoTitle;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("videoTitle","stockSize","pricePerShare","createdDate","expirationDate");
    }

    public Specification<VideoEntity> toSpec(){
        if(StringUtils.hasLength(this.videoTitle))
            this.and( this.like(this.videoTitle, "videoTitle"));
        return this.getSpec();
    }
}