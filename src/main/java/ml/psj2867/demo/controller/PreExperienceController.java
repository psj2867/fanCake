package ml.psj2867.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ml.psj2867.demo.service.video.VideoService;

@Controller
@RequestMapping("pre")
public class PreExperienceController {
    
    @Autowired
    private VideoService videoService;

   

}