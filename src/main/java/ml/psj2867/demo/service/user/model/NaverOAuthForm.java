package ml.psj2867.demo.service.user.model;

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