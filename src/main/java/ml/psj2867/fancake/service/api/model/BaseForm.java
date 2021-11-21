package ml.psj2867.fancake.service.api.model;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class BaseForm<T> {

    @Getter(value = AccessLevel.PROTECTED)
    @Setter(value = AccessLevel.NONE)
    private Specification<T> spec;

    protected Specification<T> toSpec(){
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
    protected Specification<T> equl(Object q, String fieldName){
        return (root, query, builder) -> {
            Predicate p = null;
            if( q != null )
                p = builder.equal(root.get(fieldName), q );
            return p;
        };
    }
    protected Specification<T> equl(Object q, String join, String fieldName){
        return (root, query, builder) -> {
            Predicate p = null;
            if( q != null )
                p = builder.equal(root.join(join).get(fieldName), q );
            return p;
        };
    }

}
