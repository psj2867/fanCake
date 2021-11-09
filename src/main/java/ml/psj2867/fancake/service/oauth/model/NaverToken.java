package ml.psj2867.fancake.service.oauth.model;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NaverToken{
    @NotBlank
    private String access_token;
    @Nullable
    private String token_type;
    @Nullable
    private String refresh_token;
    @Nullable
    private long expires_in;
    
}