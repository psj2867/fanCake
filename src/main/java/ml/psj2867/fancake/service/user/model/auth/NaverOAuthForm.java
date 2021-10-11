package ml.psj2867.fancake.service.user.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class NaverOAuthForm {
    
    private String code;
    private String state;

}