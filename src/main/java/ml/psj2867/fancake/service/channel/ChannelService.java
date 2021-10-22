package ml.psj2867.fancake.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.ChannelEntityDao;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.exception.notfound.ResourceNotFoundException;
import ml.psj2867.fancake.service.channel.model.ChannelDto;
import ml.psj2867.fancake.service.channel.model.ChannelForm;
import ml.psj2867.fancake.service.channel.model.ChannelListForm;
import ml.psj2867.fancake.service.channel.model.ChannesVideoslListForm;
import ml.psj2867.fancake.service.video.VideoService;
import ml.psj2867.fancake.service.video.model.VideoDto;

@Transactional
@Service
public class ChannelService  {
    
    @Autowired
    private ChannelEntityDao channelDao;  
    @Autowired
    private VideoService videoService;  

    public ChannelDto getChannel(int channelIdx){
        return channelDao.findById(channelIdx)
                        .map(ChannelDto::of)
                        .orElseThrow(  ()->  ResourceNotFoundException.of("channel", channelIdx) );
    }
    public Page<ChannelDto> getChannels(ChannelListForm channelListForm){
        return channelDao.findAll(channelListForm.toSpec(),channelListForm.toPageable())
                .map(ChannelDto::of);
    }
    public Page<VideoDto> getChannelsVideos(int channelIdx, ChannesVideoslListForm channesVideoslListForm){
        ChannelEntity channel = channelDao.findById(channelIdx)
                        .orElseThrow(  ()->  ResourceNotFoundException.of("channel", channelIdx) );
        return videoService.getVideoList(channesVideoslListForm, channel);
    }

    public ChannelEntity addChannel(ChannelForm channelForm){
        ChannelEntity channel = channelForm.toEntity();
        channelDao.save(channel);
        return channel;
    }

}