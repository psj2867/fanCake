package ml.psj2867.demo.service.stock;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.demo.dao.StockEntityDao;
import ml.psj2867.demo.entity.StockEntity;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.stock.model.StockDto;
import ml.psj2867.demo.service.stock.model.StockListForm;
import ml.psj2867.demo.service.user.UserService;
import ml.psj2867.demo.service.video.model.BuyStockForm;
import ml.psj2867.demo.service.video.model.VideoDto;

@Transactional
@Service
public class StockService  {
    
    @Autowired
    private UserService userService;
    @Autowired
    private StockEntityDao stockDao;  

    @Autowired
    private EntityManager em;

    public Page<StockDto> getUserStock(StockListForm stockListForm){
        UserEntity user = userService.getUserOrThrow();
        return stockDao.findAll(stockListForm.toSpec(user), stockListForm.toPageable())
                        .map(stockEntity -> StockDto.of(stockEntity, em));
    }

    public void buyStock(VideoEntity video , UserEntity user ,BuyStockForm form){
        StockEntity stock = form.toEntity(video, user);
        stockDao.save(stock);
    }
    

}