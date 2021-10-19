package ml.psj2867.fancake.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ml.psj2867.fancake.entity.type.VideoAutctionState;
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
    

    @Operation(description = "전체 영상 목록")
    @GetMapping("")
    public ResponseEntity<Page<VideoDto>> getRootGetVideos(VideoListForm videoListForm) {
        return ResponseEntity.ok( videoService.getVideoList(videoListForm));
    }

    @Operation(description = "영상 정보")
    @ApiResponse(responseCode = "404",description = "videoIdx 가 없는 값 일때" )
    @GetMapping("{videoIdx}")
    public ResponseEntity<VideoDto> getVideoIdxGetVideInfo(@PathVariable int videoIdx) {
        return ResponseEntity.ok( videoService.getVideo(videoIdx));
    }
    @Operation(description = "영상 state 변경")
    @ApiResponse(responseCode = "404",description = "videoIdx 가 없는 값 일때" )
    @PatchMapping("{videoIdx}")
    public ResponseEntity<MessageDto> patchVideoIdxGetVideInfo(@PathVariable int videoIdx, VideoAutctionState state ) {
        videoService.updateVideoState(videoIdx, state);
        return ResponseEntity.ok( MessageDto.success() );
    }

    @Operation(description = "영상 구매하기")
    @ApiResponse(responseCode = "201",description = "구매 성공" )
    @ApiResponse(responseCode = "400",description = "validation 또는 남아있는 크키보다 사려는 값이 더 클 때" )
    @ApiResponse(responseCode = "404",description = "videoIdx 가 없는 값 일 때" )
    @PostMapping("{videoIdx}/stock")
    public ResponseEntity<MessageDto> postVideIdxStockBuyStock(@PathVariable int videoIdx, BuyStockForm form) {      
        videoService.buyStock(videoIdx, form);    
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDto.success());
    }
    


}