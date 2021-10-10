package ml.psj2867.demo.service.api.model;

import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.exception.FieldValidException;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ListForm<T>{

    @Builder.Default
    protected static final int DEFAULT_COUNT_PER_PAGE = 10;

    @Builder.Default
    protected static final int MAX_COUNT_PER_PAGE = 50;

    @Nullable
    @Min(0)
    @Builder.Default
    protected int page = 0;

    @Nullable
    @Max(ListForm.MAX_COUNT_PER_PAGE + 1)
    @Builder.Default
    protected int countPerPage = DEFAULT_COUNT_PER_PAGE;

    @Nullable
    @Builder.Default
    protected boolean asc = true;
    
    @Nullable
    protected String sort;

    protected abstract List<String> getSortTypes();     

    @Getter(value = AccessLevel.PROTECTED)
    @Setter(value = AccessLevel.NONE)
    Specification<T> spec;

    public Specification<T> toSpec(){
        return this.spec;
    }

    protected Specification<T> and(Specification<T> addSpec){
        if( addSpec == null) return this.spec;
        if(this.spec == null){
            this.spec = addSpec;
            return this.spec;
        }
        this.spec = this.spec.and(addSpec);
        return this.spec;
    }
    protected Specification<T> or(Specification<T> addSpec){
        if( addSpec == null) return this.spec;
        if(this.spec == null){
            this.spec = addSpec;
            return this.spec;
        }
        this.spec = this.spec.or(addSpec);
        return this.spec;
    }
    protected Specification<T> like(String q, String fieldName){
        return (root, query, builder) -> {
            Predicate p = null;
            if( StringUtils.hasLength(q) )
                p = builder.like(root.get(fieldName), "%" + q + "%");
            return p;
        };
    }
    protected Specification<T> like(String q, String join, String fieldName){
        return (root, query, builder) -> {
            Predicate p = null;
            if( StringUtils.hasLength(q) )
                p = builder.like(root.join(join).get(fieldName), "%" + q + "%");
            return p;
        };
    }

    public Pageable toPageable(){
        page = ( page < 0  ) ? 0 : page;
        countPerPage = (countPerPage < 1 ) ? ListForm.DEFAULT_COUNT_PER_PAGE : countPerPage;
        countPerPage = ( ListForm.MAX_COUNT_PER_PAGE < countPerPage ) ? ListForm.MAX_COUNT_PER_PAGE : countPerPage;
        return toPageable(page, countPerPage);
    }

    private Pageable toPageable(int curr, int countPerPagge){
        PageRequest pageable = null;
        if( StringUtils.hasLength(sort) && asc ){
            isSortField(this.sort);
            Sort sort = Sort.by(Order.asc(this.sort) ) ;
            pageable = PageRequest.of(curr, countPerPagge, sort);
        }else if( StringUtils.hasLength(sort) ){
            isSortField(this.sort);
            Sort sort = Sort.by(Order.desc(this.sort) ) ;
            pageable = PageRequest.of(curr, countPerPagge, sort);
        }else{
            pageable = PageRequest.of(curr, countPerPagge);            
        }
        return pageable;
    }

    protected void isSortField(String sort){
        if(! getSortTypes().contains(sort)){
            String message =  String.format("Available sort types - %s",this.getSortTypes());
            ErrorDto error = ErrorDto.of("api.sort.notValidType","sort",sort ,message );
            throw new FieldValidException(error);
        }
    }
}