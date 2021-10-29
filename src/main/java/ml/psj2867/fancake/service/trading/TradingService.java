package ml.psj2867.fancake.service.trading;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.TradingHistoryEntityDao;
import ml.psj2867.fancake.entity.TradingHistoryEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.service.trading.model.TradingHistoryDto;
import ml.psj2867.fancake.service.trading.model.TradingHistoryListForm;
import ml.psj2867.fancake.service.trading.model.TradingTypeEnum;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.video.model.BuyStockForm;

@Transactional
@Service
public class TradingService  {
    
    @Autowired
    private TradingHistoryEntityDao tradingDao;
    @Autowired
    private UserService userService;

    
    public Page<TradingHistoryDto> getTradingList(TradingHistoryListForm tradingListForm){
        UserEntity user = userService.getUserOrThrow();
        return tradingDao.findAll(tradingListForm.toSpec(user), tradingListForm.toPageable())
                            .map(TradingHistoryDto::of);
    }

    public void saveBuyTrading(VideoEntity video, UserEntity user, BuyStockForm form){
        TradingHistoryEntity tradingHistoryEntity = TradingHistoryEntity.builder()
                            .channelID(video.getChannel().getChannnelId())
                            .channelTitle(video.getChannel().getChannelTitle())
                            .createdDate(LocalDateTime.now())
                            .owner(user)
                            .userId(user.getId())
                            .price(form.calcAmmountOfStock(video))
                            .size(form.getSize())
                            .type(TradingTypeEnum.BUY)
                            .userAfterBalance(user.getBalance())
                            .videoTitle(video.getVideoTitle())
                            .videoId(video.getVideoId())
                            .build();
        tradingDao.save(tradingHistoryEntity);
    }

}