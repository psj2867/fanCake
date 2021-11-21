package ml.psj2867.fancake.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ml.psj2867.fancake.service.channel.ChannelService;
import ml.psj2867.fancake.service.channel.model.ChannelDto;
import ml.psj2867.fancake.service.channel.model.ChannelListForm;
import ml.psj2867.fancake.service.channel.model.ChannesVideoslListForm;
import ml.psj2867.fancake.service.video.model.VideoDto;


@Api(basePath = "api/channels", description="채널 관련 정보")
@RestController
@RequestMapping("api/channels")
public class ChannelsRestController {
    
    @Autowired
    private ChannelService channelService;
    
    
    @Operation(description = "전체 채널 목록")
    @GetMapping("")
    public Page<ChannelDto> getRootGetVideos(ChannelListForm channelListForm) {
        return channelService.getChannels(channelListForm);
    }

    @Operation(description = "채널 정보")
    @GetMapping("{channelIdx}")
    public ChannelDto getIdxGetVideoInfo(@PathVariable int channelIdx) {
        return channelService.getChannel(channelIdx);
    }

    @Operation(description = "채널의 동영상 목록")
    @GetMapping("{channelIdx}/videos")
    public Page<VideoDto> getRootGetVideos(@PathVariable int channelIdx, ChannesVideoslListForm channesVideoslListForm ) {
        return channelService.getChannelsVideos(channelIdx, channesVideoslListForm);
    }


}