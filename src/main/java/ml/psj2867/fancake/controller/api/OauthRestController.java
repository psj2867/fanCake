package ml.psj2867.fancake.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import ml.psj2867.fancake.configure.ConfigureProperties;
import ml.psj2867.fancake.configure.security.TokenDto;
import ml.psj2867.fancake.service.oauth.AuthService;
import ml.psj2867.fancake.service.oauth.model.NaverOAuthForm;
import ml.psj2867.fancake.service.oauth.model.NaverToken;

@Api(description="sns 로그인 관련")
@Controller
@RequestMapping("api/oauth")
public class OauthRestController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ConfigureProperties properties;
    

    @ResponseBody
    @GetMapping("naver") 
    public String getNaverUrl(){
        String callBackUrl = "http://"+properties.getHost()+ "/api/oauth/naver/callback";
        return authService.getNaverOAuthUri(callBackUrl).toString();
    }
    
    @ResponseBody
    @PostMapping("naver") 
    public String postNaverAccessToken(@RequestBody @Validated NaverToken naverToken){
        return authService.loginNaverUserOrAdd(naverToken).getAccessToken();
    }
    
    @ResponseBody
    @GetMapping("naver/callback") 
    public Object getNaverCallback(@Validated NaverOAuthForm naverOAuthForm){
        Map<String,Object> result = new HashMap<>();
        result.put("code", naverOAuthForm.getCode());
        NaverToken naverToken  = authService.getNaverAccessToekn(naverOAuthForm);
        result.put("naver_token", naverToken.getAccess_token());
        TokenDto serverToken = authService.loginNaverUserOrAdd(naverToken);
        result.put("server_token", serverToken.getAccessToken());
        return result;
    }

}