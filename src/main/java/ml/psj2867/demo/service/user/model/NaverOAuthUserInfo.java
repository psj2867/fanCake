package ml.psj2867.demo.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class NaverOAuthUserInfo{
    private String id;
    private String email;
    private String mobile;
    private String mobile_e164;
    private String name;    
}