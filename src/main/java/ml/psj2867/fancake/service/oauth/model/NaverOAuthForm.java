package ml.psj2867.fancake.service.oauth.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class NaverOAuthForm {

    @NotNull
    private String code;
    
    private String state;

}