package ml.psj2867.fancake.configure.security;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class TokenDto{
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
    
}