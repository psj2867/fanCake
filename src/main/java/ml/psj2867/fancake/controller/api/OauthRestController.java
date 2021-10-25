package ml.psj2867.fancake.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ml.psj2867.fancake.service.user.AuthService;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthForm;

@Controller
@RequestMapping("api/oauth")
public class OauthRestController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("naver") 
    public String naverCallback(NaverOAuthForm naverOAuthForm){
        return authService.loginNaverUserOrAdd(naverOAuthForm).getAccessToken();
    }

}