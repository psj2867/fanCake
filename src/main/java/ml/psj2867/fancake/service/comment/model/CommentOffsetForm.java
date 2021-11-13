package ml.psj2867.fancake.service.comment.model;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.CommentEntity;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.service.api.model.BaseForm;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CommentOffsetForm extends BaseForm<CommentEntity> {
    private static final String sortColumn = "idx";
    private static final String videoRootName = "video";

    @Nullable
    private Integer offset;
    @Builder.Default
    @Nullable
    private boolean prev = false;
    @Min(1)
    @Max(100)
    @Builder.Default
    private int limit = 10;

    public boolean next(){return ! this.isPrev();}

    public Specification<CommentEntity> toSpec(VideoEntity video){
        if(offset!=null){
            if(this.desc())
                this.and((root, query, builder) -> {
                    return builder.greaterThan(root.get(sortColumn), offset);
                });
            else 
                this.and((root, query, builder) -> {
                    return builder.lessThan(root.get(sortColumn), offset);
                });
        }
    
        this.and((root, query, builder) -> {
            return builder.equal(root.get(videoRootName), video);
        });
    
        return super.toSpec();
        // this.and((root, query, builder) -> {
        //     Subquery<Integer> maxCommentIdx = query.subquery(Integer.class);
        //     Root<CommentEntity> maxCommentRoot = maxCommentIdx.from(CommentEntity.class);
        //     maxCommentIdx = maxCommentIdx
        //         .select(builder.max(maxCommentRoot.get(sortColumn)))
        //         .where(builder.equal(maxCommentRoot.get(videoRootName), video));
        //     return builder.lessThanOrEqualTo(root.get(sortColumn), maxCommentIdx);
        // });
    }
    public Pageable toPageable(){
        Sort sort = null;
        if(this.desc())
            sort = Sort.by(Order.desc(sortColumn));
        else
            sort = Sort.by(Order.asc(sortColumn));
        
        Pageable pageable = PageRequest.of(0, this.limit, sort);
        return pageable;
    }
    public boolean desc(){
        return this.next() || this.offset == null;
    }

}
