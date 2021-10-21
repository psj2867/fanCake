package ml.psj2867.fancake.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.VideoEntityDao;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.entity.type.VideoAuctionState;
import ml.psj2867.fancake.exception.bad.FieldValidException;
import ml.psj2867.fancake.exception.notfound.ResourceNotFoundException;
import ml.psj2867.fancake.service.stock.StockService;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.video.model.BuyStockErrorDto;
import ml.psj2867.fancake.service.video.model.BuyStockForm;
import ml.psj2867.fancake.service.video.model.VideoDto;
import ml.psj2867.fancake.service.video.model.VideoForm;
import ml.psj2867.fancake.service.video.model.VideoListForm;

@Transactional
@Service
public class VideoService  {
    
    @Autowired
    private VideoEntityDao videoDao;
    @Autowired
    private StockService stockService;    
    @Autowired
    private UserService userService;

    public void buyStock(int videoIdx, BuyStockForm form){
        UserEntity user = userService.getUserOrThrow();
        VideoEntity video = videoDao.findById(videoIdx)
                                .orElseThrow( ()->  ResourceNotFoundException.of("video", videoIdx) );
        long remainSize = video.getStockSize() - video.getSize();
        if(  remainSize < form.getSize() ){
            BuyStockErrorDto error = BuyStockErrorDto.builder()
                                                        .code("video.buyStock.outOfSize")
                                                        .rejectedValue(form.getSize())
                                                        .reaminSize(remainSize)
                                                        .build();
            throw FieldValidException.of(error);
        }
        stockService.buyStock(video, user, form);
    }

    public void updateVideoState(int videoIdx, VideoAuctionState state){
      VideoEntity video = videoDao.findById(videoIdx)
          .orElseThrow( ()->  ResourceNotFoundException.of("video", videoIdx) );
      video.setAuctionState(state);
      videoDao.save(video);
    }
    public VideoDto getVideo(int videoIdx){
        return videoDao.findById(videoIdx)
            .map(VideoDto::of)
            .orElseThrow( ()->  ResourceNotFoundException.of("video", videoIdx) );
    }

    public Page<VideoDto> getVideoList(VideoListForm form){
        Page<VideoEntity> items = videoDao.findAll(form.toSpec(), form.toPageable());
        return items.map(VideoDto::of);
    }
    

    public VideoEntity addVideoList(VideoForm form){
        return videoDao.save(form.toEntity());
    }

}