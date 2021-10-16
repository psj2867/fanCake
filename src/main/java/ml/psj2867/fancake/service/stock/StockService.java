package ml.psj2867.fancake.service.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.StockEntityDao;
import ml.psj2867.fancake.entity.StockEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.service.stock.model.StockDto;
import ml.psj2867.fancake.service.stock.model.StockListForm;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.video.model.BuyStockForm;

@Transactional
@Service
public class StockService  {
    
    @Autowired
    private UserService userService;
    @Autowired
    private StockEntityDao stockDao;  

    public Page<StockDto> getUserStock(StockListForm stockListForm){
        UserEntity user = userService.getUserOrThrow();
        return stockDao.findAll(stockListForm.toSpec(user), stockListForm.toPageable())
                        .map(StockDto::of);
    }

    public void buyStock(VideoEntity video , UserEntity user ,BuyStockForm form){
        StockEntity stock = form.toEntity(video, user);
        stockDao.save(stock);
    }
    

}