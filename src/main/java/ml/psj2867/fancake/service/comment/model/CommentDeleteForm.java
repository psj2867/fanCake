package ml.psj2867.fancake.service.comment.model;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDeleteForm {

    @NotNull
    private Integer commentIdx;

}
