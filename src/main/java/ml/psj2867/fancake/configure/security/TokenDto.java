package ml.psj2867.fancake.configure.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDto{
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
    
}