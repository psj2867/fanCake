package ml.psj2867.fancake.controller.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.service.comment.CommentService;
import ml.psj2867.fancake.service.comment.model.CommentDto;
import ml.psj2867.fancake.service.comment.model.CommentOffsetForm;

@Slf4j
@Controller
public class StompController {
    public static final String SUBSCRIBE_MAPPING_FORMMAT = "/sub/videos/%s/comments";
    
    @Autowired
    private  CommentService commentService;

    @SubscribeMapping("/videos/{videoIdx}/comments")
    public List<CommentDto> subscribeVideosComments(@DestinationVariable Integer videoIdx){
        CommentOffsetForm commentOffsetForm = CommentOffsetForm.builder()
                                                                .limit(10)
                                                                .build(); 
        return commentService.getComments(videoIdx, commentOffsetForm).toList();       
    }

    @MessageExceptionHandler
    public String handleException(Exception e) {
        log.error("stomp exceptoin", e);
        return "exceptoin";
    }

    public static String getSubscribeDestination(int videoIdx){
        return String.format(SUBSCRIBE_MAPPING_FORMMAT, videoIdx);
    }

}
