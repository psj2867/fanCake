package ml.psj2867.fancake.service.stock.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.entity.StockEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.api.model.ListForm;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "sort type - [video,price,createdDate]")
public class StockListForm extends ListForm<StockEntity> {
    
    @Nullable
    private String videoTitle;

    @Schema(defaultValue = "false")
    @Builder.Default
    private boolean confirm = false;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("video","price","createdDate");
    }

    public Specification<StockEntity> toSpec(final UserEntity user){
        if(StringUtils.hasLength(this.videoTitle))
            this.and( this.like(this.videoTitle, "video", "videoTitle"));
        if(this.confirm)
            this.and((root, query, builder) -> {
                return builder.lessThan(root.join("video").get("expirationDate"), LocalDateTime.now());
            });
        return this.getSpec();
    }
}