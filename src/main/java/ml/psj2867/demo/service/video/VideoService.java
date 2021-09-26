package ml.psj2867.demo.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.demo.dao.VideoEntityDao;
import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.video.model.VideoDto;
import ml.psj2867.demo.service.video.model.VideoListForm;
import ml.psj2867.demo.service.youtube.YoutubeService;

@Transactional
@Service
public class VideoService  {
    
    @Autowired
    private VideoEntityDao videoDao;
    
    @Autowired
    private YoutubeService youtubeService;


    public Page<VideoEntity> getVideoList(VideoListForm form){
        Page<VideoEntity> items = videoDao.findAll(form.toSpec(), form.toPageable(1));
        return items;
    }

}