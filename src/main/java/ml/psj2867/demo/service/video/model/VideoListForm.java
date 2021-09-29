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
    @Builder.Default
    private int MAX_COUNT_PER_PAGE = 50;

    private String q;
    private Integer page;
    private Integer countPerPage;
    private String sort;
    @Builder.Default
    private boolean asc = true;

    public Specification<VideoEntity> toSpec(){
        return (root, query, builder) -> {
            Predicate p = null;
            if(  StringUtils.hasLength(q) ){
               p = builder.like(root.get("videoTitle"), "%" + q + "%");
            }
            return p;
        };
    }

    public Pageable toPageable(){
        page = (page == null ||  page < 0  ) ? 0 : page;
        countPerPage = (countPerPage == null ||  countPerPage < 1 ) ? DEFAULT_COUNT_PER_PAGE : countPerPage;
        countPerPage = countPerPage > MAX_COUNT_PER_PAGE ? MAX_COUNT_PER_PAGE : countPerPage;
        return toPageable(page, countPerPage);
    }

    private Pageable toPageable(int curr, int countPerPagge){
        PageRequest pageable = null;
        if( StringUtils.hasLength(sort) && asc ){
            Sort sort = Sort.by(Order.asc(this.sort) ) ;
            pageable = PageRequest.of(curr, countPerPagge, sort);
        }else if( StringUtils.hasLength(sort) ){
            Sort sort = Sort.by(Order.desc(this.sort) ) ;
            pageable = PageRequest.of(curr, countPerPagge, sort);
        }else{
            pageable = PageRequest.of(curr, countPerPagge);            
        }
        return pageable;
    }
}