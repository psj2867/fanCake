package ml.psj2867.fancake.configure.security;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class SignOnTokenDto extends TokenDto{

    private boolean isNew;
    
    public static SignOnTokenDto of(TokenDto tokenDto){
        return SignOnTokenDto.builder()
            .refreshToken(tokenDto.getRefreshToken())
            .accessTokenExpiresIn(tokenDto.getAccessTokenExpiresIn())
            .accessToken(tokenDto.getAccessToken())
            .isNew(true)
            .build();
    }
}