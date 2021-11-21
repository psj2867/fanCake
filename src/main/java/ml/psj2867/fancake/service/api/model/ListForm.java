package ml.psj2867.fancake.service.api.model;

import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.exception.bad.FieldValidException;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ListForm<T> extends BaseForm<T>{

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
   

    public Pageable toPageable(){
        page = ( page < 0  ) ? 0 : page;
        countPerPage = (countPerPage < 1 ) ? ListForm.DEFAULT_COUNT_PER_PAGE : countPerPage;
        countPerPage = ( ListForm.MAX_COUNT_PER_PAGE < countPerPage ) ? ListForm.MAX_COUNT_PER_PAGE : countPerPage;
        return toPageable(page, countPerPage,null);
    }

    private Pageable toPageable(int curr, int countPerPagge, ListForm<T> nullForInner){
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