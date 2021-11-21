package ml.psj2867.fancake.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.fancake.util.MessageDto;


@RestController
@RequestMapping("api")
public class ApiRestController {

    @RequestMapping("*")
    public ResponseEntity<Object> notFound(){  
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto.of("Unmapped Url"));
    }

}