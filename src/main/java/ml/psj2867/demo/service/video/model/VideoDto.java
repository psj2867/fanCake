package ml.psj2867.demo.service.video.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.demo.entity.StockEntity;
import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.channel.model.ChannelDto;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class VideoDto{

    private ChannelDto channel;
    private String title;
    private double marketCap;
    private double pricePerShare;
    private int totlaAmount;
    private int currentAmount;
    private int videoIdx;
    private LocalDateTime expirationDate;
    private boolean isOnSale;

    public static VideoDto of(VideoEntity videoEntity, EntityManager em){
        VideoDto videoDto = of(videoEntity);
        videoDto.setCurrentAmount(videoEntity.getCurrentStockSize(em));
        return videoDto;
    }
    private static VideoDto of(VideoEntity videoEntity){
        return VideoDto.builder()
                    .title(videoEntity.getVideoTitle())
                    .expirationDate(videoEntity.getExpirationDate())
                    .marketCap(videoEntity.getPricePerShare() * videoEntity.getStockSize())
                    .pricePerShare(videoEntity.getPricePerShare())
                    .totlaAmount(videoEntity.getStockSize())
                    .channel(ChannelDto.of(videoEntity.getChannel()))
                    .videoIdx(videoEntity.getIdx())
                    .isOnSale(checkOnSale(videoEntity))
                    .build();
    }
    private static boolean checkOnSale(VideoEntity videoEntity){
        return videoEntity.getExpirationDate().isAfter(LocalDateTime.now());
    }

}