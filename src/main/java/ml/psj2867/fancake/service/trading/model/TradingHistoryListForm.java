package ml.psj2867.fancake.service.trading.model;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.TradingHistoryEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.api.model.ListForm;


@ApiModel(description = "sort type - [videoTitle,price,createdDate]")
@NoArgsConstructor
@SuperBuilder
public class TradingHistoryListForm extends ListForm<TradingHistoryEntity> { 

    @Nullable
    private String videoTitle;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("videoTitle","price","createdDate");
    }
    public Specification<TradingHistoryEntity> toSpec() {
        if(StringUtils.hasLength(this.videoTitle))
            this.and(this.like(this.videoTitle,"videoTitle"));
        return this.getSpec();
    }
    public Specification<TradingHistoryEntity> toSpec(UserEntity user) {
        return this.and((root, query, builder) -> {
          return builder.equal(root.get("owner"), user);
        });
    }
}