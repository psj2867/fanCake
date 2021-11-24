package ml.psj2867.fancake.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ml.psj2867.fancake.util.MessageDto;


@Controller
@RequestMapping("api")
public class ApiRestController {

    @ResponseBody
    @RequestMapping("*")
    public ResponseEntity<Object> notFound(){  
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto.of("Unmapped Url"));
    }

    @GetMapping("stomp")
    public String stompTestStatic(){
        return "forward:/static/stomp.html";
    }
}