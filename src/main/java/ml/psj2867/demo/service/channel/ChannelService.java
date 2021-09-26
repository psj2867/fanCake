package ml.psj2867.demo.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.demo.dao.ChannelEntityDao;
import ml.psj2867.demo.entity.ChannelEntity;
import ml.psj2867.demo.service.channel.model.ChannelForm;

@Transactional
@Service
public class ChannelService  {
    
    @Autowired
    private ChannelEntityDao channelDao;  

    public ChannelEntity setChannel(ChannelForm channelForm){
        ChannelEntity channel = channelForm.toEntity();
        channelDao.save(channel);
        return channel;
    }

}