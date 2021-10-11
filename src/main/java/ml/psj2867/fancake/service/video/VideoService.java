package ml.psj2867.fancake.service.video;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.VideoEntityDao;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.exception.FieldValidException;
import ml.psj2867.fancake.exception.NotFoundException;
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
    @Autowired
    private EntityManager em;

    public void buyStock(int videoIdx, BuyStockForm form){
        UserEntity user = userService.getUserOrThrow();
        VideoEntity video = videoDao.findById(videoIdx)
                                .orElseThrow( ()->  NotFoundException.of("video", videoIdx) );
        int remainSize = video.getStockSize() - video.getCurrentStockSize(em);
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

    public VideoDto getVideo(int videoIdx){
        return videoDao.findById(videoIdx)
            .map( videoEntitiyL -> VideoDto.of(videoEntitiyL, em))
            .orElseThrow( ()->  NotFoundException.of("video", videoIdx) );
    }

    public Page<VideoDto> getVideoList(VideoListForm form){
        Page<VideoEntity> items = videoDao.findAll(form.toSpec(), form.toPageable());
        return items.map(videoDtoL -> VideoDto.of(videoDtoL, em));
    }
    

    public VideoEntity addVideoList(VideoForm form){
        return videoDao.save(form.toEntity());
    }

}