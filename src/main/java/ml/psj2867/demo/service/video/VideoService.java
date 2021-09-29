package ml.psj2867.demo.service.video;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import groovy.util.logging.Log4j2;
import ml.psj2867.demo.dao.VideoEntityDao;
import ml.psj2867.demo.entity.StockEntity;
import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.video.model.VideoDto;
import ml.psj2867.demo.service.video.model.VideoForm;
import ml.psj2867.demo.service.video.model.VideoListForm;
import ml.psj2867.demo.service.youtube.YoutubeService;

@Transactional
@Service
public class VideoService  {
    
    @Autowired
    private VideoEntityDao videoDao;
    
    @Autowired
    private YoutubeService youtubeService;
    @Autowired
    private EntityManager em;

    public Page<VideoDto> getVideoList(VideoListForm form){
        Page<VideoEntity> items = videoDao.findAll(form.toSpec(), form.toPageable());
        return items.map(entiytyL -> VideoDto.builder()
                                                .title(entiytyL.getVideoTitle())
                                                .expirationDate(entiytyL.getExpirationDate())
                                                .marketCap(entiytyL.getPricePerShare() * entiytyL.getStockSize())
                                                .pricePerShare(entiytyL.getPricePerShare())
                                                .currentAmount(countStock(entiytyL))
                                                .build()
        );
    }
    private int countStock(VideoEntity videoEntity){
        return videoEntity.getSotkcs().stream().mapToInt(stockL -> stockL.getSize()).sum();
    }
    public int countStockByEm(VideoEntity videoEntity){
        int videoIdx = videoEntity.getIdx();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> q = cb.createQuery(Number.class);
        Root<StockEntity> stock= q.from(StockEntity.class);

        q.select(cb.sum(stock.get("size")).alias("sum"));
        q.where(cb.equal(stock.get("video"), videoIdx));
        return Optional.ofNullable( em.createQuery(q).getSingleResult() )
                        .map(Number::intValue)
                        .orElse(0);
    }

    public VideoEntity addVideoList(VideoForm form){
        return videoDao.save(form.toEntity());
    }

}