package ml.psj2867.fancake.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import ml.psj2867.fancake.service.user.AuthService;
import ml.psj2867.fancake.service.user.model.auth.NaverToken;

@Api(description="sns 로그인 관련")
@Controller
@RequestMapping("api/oauth")
public class OauthRestController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @PostMapping("naver") 
    public String postNaverAccessToken(@RequestBody @Validated NaverToken naverToken){
        return authService.loginNaverUserOrAdd(naverToken).getAccessToken();
    }

}