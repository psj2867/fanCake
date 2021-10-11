package ml.psj2867.fancake.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {    

    
    @Autowired
    private MessageSource messageSource;
    
    @RequestMapping("")
    public String getMain() {
        return "redirect:/static/fanCake/index.html";
    }


    
    @RequestMapping("test")
    public @ResponseBody String getTest() {
        return messageSource.getMessage("NotBlank",null,"", Locale.ROOT);
    }

}