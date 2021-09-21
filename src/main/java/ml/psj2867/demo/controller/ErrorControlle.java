package ml.psj2867.demo.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("error")
public class ErrorControlle {
 
    @GetMapping("error")
    public String getRoot(){
        return "main";
    }
    

}