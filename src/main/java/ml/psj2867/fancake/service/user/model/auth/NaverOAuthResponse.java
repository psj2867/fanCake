package ml.psj2867.fancake.service.user.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaverOAuthResponse{
    private String resultcode;
    private String message;
    private NaverOAuthUserInfo response;
    
}