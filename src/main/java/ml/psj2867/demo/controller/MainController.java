package ml.psj2867.demo.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
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

    
    @RequestMapping("")
    public String getMain() {
        return "redirect:/static/fanCake/index.html";
    }

    @RequestMapping("temp")
    public @ResponseBody Object getTemp() {
        VideoListForm videoListForm = VideoListForm.builder()
                                        .countPerPage(5)
                                        .sort("createdDate")
                                        .asc(false)
                                        .build();
        return videoService.getVideoList(videoListForm);

    }

    @RequestMapping("auh")
    public @ResponseBody Object getAuth() {
        return SecurityUtil.getAuth().get();
    }



}