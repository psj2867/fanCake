package ml.psj2867.fancake.service.user.model;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.api.model.ListForm;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListForm extends ListForm<UserEntity> {

    @Nullable
    private String name;

    @Override
    protected List<String> getSortTypes() {
        return Arrays.asList("name","createdDate");
    }

    @Override
    public Specification<UserEntity> toSpec(){
        if(StringUtils.hasLength(this.name))
            this.and( this.like(this.name, "name"));
        return this.getSpec();
    }
}