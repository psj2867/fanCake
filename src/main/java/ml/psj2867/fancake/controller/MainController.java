package ml.psj2867.fancake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {    

    
    @RequestMapping("")
    public String getMain() {
        return "redirect:/static/index.html";
    }

    @RequestMapping("test")
    public @ResponseBody Object test() {
        return "";
    }



}