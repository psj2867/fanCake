package ml.psj2867.demo.service.video.model;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ml.psj2867.demo.entity.VideoEntity;

@Builder
@AllArgsConstructor
@Data
public class VideoListForm {
    @Builder.Default
    private int DEFAULT_COUNT_PER_PAGE = 10;

    private String q;
    private String sort;
    private String asc;

    public Specification<VideoEntity> toSpec(){
        return (root, query, builder) -> {
            Predicate p = null;
            if(  StringUtils.hasLength(q) ){
               p = builder.like(root.get("videoTitle"), "%" + q + "%");
            }
            return p;
        };
    }
    public Pageable toPageable(int curr){
        return toPageable(curr, this.DEFAULT_COUNT_PER_PAGE);
    }
    public Pageable toPageable(int curr, int countPerPagge){
        PageRequest pageable = null;
        if( StringUtils.hasLength(sort) ){
            Sort sort = Sort.by(Order.asc(this.sort) ) ;
            pageable = PageRequest.of(curr, countPerPagge, sort);
        }else{
            pageable = PageRequest.of(curr, countPerPagge);            
        }
        return pageable;
    }
}