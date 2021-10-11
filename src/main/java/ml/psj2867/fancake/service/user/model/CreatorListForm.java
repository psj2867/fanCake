package ml.psj2867.fancake.service.user.model;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.configure.security.AuthEnum;
import ml.psj2867.fancake.entity.AuthoritiesEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.api.model.ListForm;

@Getter
@Setter
@Builder
public class CreatorListForm extends ListForm<UserEntity> {

    @Nullable
    private String name;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("name","createdDate");
    }

    @Override
    public Specification<UserEntity> toSpec(){        
        this.and((root, query, builder) -> {
            Subquery<AuthoritiesEntity> subquery = query.subquery(AuthoritiesEntity.class);
            Root<AuthoritiesEntity> subqueryRoot = subquery.from(AuthoritiesEntity.class);
            Predicate subQueryWhere = builder.equal(subqueryRoot.get("auth"), AuthEnum.CREATOR.name());
            return root.get("idx").in(subquery.select(subqueryRoot.get("user")).where(subQueryWhere));
        });
        if(StringUtils.hasLength(this.name))
            this.and( this.like(this.name, "name"));
        return this.getSpec();
    }
}