package ml.psj2867.fancake.controller.api;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ml.psj2867.fancake.entity.type.VideoAuctionState;
import ml.psj2867.fancake.service.comment.CommentService;
import ml.psj2867.fancake.service.comment.model.CommentDto;
import ml.psj2867.fancake.service.comment.model.CommentForm;
import ml.psj2867.fancake.service.comment.model.CommentOffsetForm;
import ml.psj2867.fancake.service.video.VideoService;
import ml.psj2867.fancake.service.video.model.BuyStockForm;
import ml.psj2867.fancake.service.video.model.VideoDto;
import ml.psj2867.fancake.service.video.model.VideoListForm;
import ml.psj2867.fancake.util.MessageDto;


@Api(basePath = "api/videos", description="영상 관련 정보")
@RestController
@RequestMapping("api/videos")
public class VideosRestController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private CommentService commentService;
    

    @Operation(description = "전체 영상 목록")
    @GetMapping("")
    public Page<VideoDto> getRootGetVideos(@Validated VideoListForm videoListForm) {
        return videoService.getVideoList(videoListForm);
    }

    @Operation(description = "영상 정보")
    @ApiResponse(responseCode = "404",description = "videoIdx 가 없는 값 일때" )
    @GetMapping("{videoIdx}")
    public VideoDto getVideoIdxGetVideInfo(@PathVariable int videoIdx) {
        return videoService.getVideoOrThrow(videoIdx);
    }
    @Operation(description = "영상 state 변경")
    @ApiResponse(responseCode = "404",description = "videoIdx 가 없는 값 일때" )
    @PatchMapping("{videoIdx}")
    public MessageDto patchVideoIdxGetVideInfo(@PathVariable int videoIdx,@Validated @RequestBody VideoAuctionState state ) {
        videoService.updateVideoState(videoIdx, state);
        return MessageDto.success();
    }

    @Operation(description = "영상 구매하기")
    @ApiResponse(responseCode = "201",description = "구매 성공" )
    @ApiResponse(responseCode = "400",description = "validation 또는 남아있는 크키보다 사려는 값이 더 클 때, 돈이 모자랄 때, 이미 종료된 영상일 때" )
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @ApiResponse(responseCode = "404",description = "videoIdx 가 없는 값 일 때" )
    @PostMapping("{videoIdx}/stock")
    public ResponseEntity<MessageDto> postVideIdxStockBuyStock(@PathVariable int videoIdx,@Validated @RequestBody BuyStockForm form) {      
        videoService.buyStock(videoIdx, form);    
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDto.success());
    }

    @Operation(description = "영상 댓글 - offset이 비어있으면 prev 무시, 가장 최근 limit 만큼")
    @GetMapping("{videoIdx}/comments")
    public Page<CommentDto> getComments(@PathVariable int videoIdx, @Validated CommentOffsetForm commentOffsetForm){
        return commentService.getComments(videoIdx, commentOffsetForm);
    }
    @PostMapping("{videoIdx}/comments")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    public MessageDto postComments(@PathVariable int videoIdx, @Validated @RequestBody CommentForm commentForm){
        commentService.doComment(videoIdx, commentForm);
        return MessageDto.success();
    }
    @DeleteMapping("{videoIdx}/comments")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @ApiResponse(responseCode = "403",description = "작성자 아님" )
    @ApiResponse(responseCode = "404",description = "없는 댓글" )
    public MessageDto deleteComments(@PathVariable int videoIdx, @Validated @NotNull @RequestBody int commentIdx){
        commentService.deleteComment(videoIdx, commentIdx);
        return MessageDto.success();
    }

}