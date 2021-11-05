package ml.psj2867.fancake.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ml.psj2867.fancake.service.user.AuthService;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthForm;
import ml.psj2867.fancake.service.user.model.auth.NaverToken;

@Controller
@RequestMapping("api/oauth")
public class OauthRestController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @PostMapping("naver") 
    public String naverCallback(@RequestBody @Validated NaverToken naverToken){
        return authService.loginNaverUserOrAdd(naverToken).getAccessToken();
    }

    @GetMapping("naver_test") 
    public String naverTest(NaverOAuthForm naverOAuthForm){
        return "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=PdW0nmtpfhryHj5UrU_C&redirect_uri=http%3A%2F%2Fdev2.psj2867.com%3A8080%2Fapi%2Foauth%2Fnaver&state=897364419183268226652408826015092631592";
    }

}