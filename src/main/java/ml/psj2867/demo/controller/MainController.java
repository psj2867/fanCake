package ml.psj2867.demo.controller;

import java.sql.SQLException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ml.psj2867.demo.service.video.VideoService;
import ml.psj2867.demo.service.video.model.VideoForm;
import ml.psj2867.demo.service.video.model.VideoListForm;
import ml.psj2867.demo.util.SecurityUtil;

@Controller
public class MainController {
    
    
    @Autowired
    private VideoService videoService;

    
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