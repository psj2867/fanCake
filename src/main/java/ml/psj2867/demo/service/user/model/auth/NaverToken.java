package ml.psj2867.demo.service.user.model.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NaverToken{
    private String token_type;
    private String access_token;
    private String refresh_token;
    private long expires_in;
    
}