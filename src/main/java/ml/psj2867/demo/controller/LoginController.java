package ml.psj2867.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.AuthService;
import ml.psj2867.demo.service.user.UserService;
import ml.psj2867.demo.service.user.model.NaverOAuthForm;
import ml.psj2867.demo.service.user.model.UserForm;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private AuthService authService;
    
    @GetMapping("")
    public ModelAndView getLogin(){
        ModelAndView mav = new ModelAndView("login/login");
        mav.addObject("naver_oauth_url", authService.getNaverOAuthUri());
        return mav;
    }
    @PostMapping("")
    public @ResponseBody Object login(UserForm userForm, HttpServletResponse res) {
        try {
            final UserEntity user = authService.loginOriginUser(userForm);
            authService.login(user,  res);
            return "succss : " + user.getId();
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("naver/callback")
    public @ResponseBody Object naverLogin(NaverOAuthForm naverOAuthForm,HttpServletResponse res) {        
        try {
            final UserEntity user =  authService.loginNaverUserOrAdd(naverOAuthForm);
            authService.login(user, res);
            return "succss : " + user.getId();
        } catch (Exception e) {
            return "error";
        }
    }


    

}