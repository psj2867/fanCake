package ml.psj2867.fancake.service.trading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.TradingHistoryEntityDao;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.trading.model.TradingHistoryDto;
import ml.psj2867.fancake.service.trading.model.TradingHistoryListForm;
import ml.psj2867.fancake.service.user.UserService;

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

}