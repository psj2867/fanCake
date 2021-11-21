package ml.psj2867.fancake.controller.websocket;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.entity.CommentEntity;
import ml.psj2867.fancake.service.comment.CommentService;
import ml.psj2867.fancake.service.comment.model.CommentDto;
import ml.psj2867.fancake.service.comment.model.CommentForm;
import ml.psj2867.fancake.service.comment.model.CommentOffsetForm;

@Slf4j
@Controller
public class StompController {
    
    @Autowired
    private  SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private  CommentService commentService;

    @MessageMapping("/videos/{videoIdx}/comments")
    public void publishVideosComments(@DestinationVariable Integer videoIdx, @Validated CommentForm commentForm, StompHeaderAccessor accessor ){
        SecurityContextHolder.getContext().setAuthentication((Authentication) accessor.getUser());
        
        CommentEntity comment = commentService.insertComment(videoIdx, commentForm);

        String detination = "/sub/videos/" + videoIdx + "/comments";
        List<CommentDto> payload = Arrays.asList(CommentDto.of(comment));
        messagingTemplate.convertAndSend(detination, payload);
        return;
    }
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

}
