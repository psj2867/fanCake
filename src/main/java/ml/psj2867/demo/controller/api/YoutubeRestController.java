package ml.psj2867.demo.controller.api;

import com.google.api.services.youtube.model.SearchListResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.demo.service.youtube.YoutubeService;


@RestController
@RequestMapping("api/youtube")
public class YoutubeRestController {
    
    @Autowired
    private YoutubeService youtubeService;

    @GetMapping("channel")
    public ResponseEntity<SearchListResponse> postAdvance(String q){
        return youtubeService.getChannelList(q)
                .map(reponseL -> ResponseEntity.ok(reponseL) )
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

}