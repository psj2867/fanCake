package ml.psj2867.fancake.service.video.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.service.channel.model.ChannelDto;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class VideoDto{

    private ChannelDto channel;
    private String title;
    private String videoId;
    private double marketCap;
    private double pricePerShare;
    private long totalAmount;
    private long currentAmount;
    private int videoIdx;
    private LocalDateTime expirationDate;
    private boolean isOnSale;

    public static VideoDto of(VideoEntity videoEntity){
        return VideoDto.builder()
                    .title(videoEntity.getVideoTitle())
                    .currentAmount(videoEntity.getSize())
                    .expirationDate(videoEntity.getExpirationDate())
                    .marketCap(videoEntity.getPricePerShare() * videoEntity.getStockSize())
                    .pricePerShare(videoEntity.getPricePerShare())
                    .totalAmount(videoEntity.getStockSize())
                    .channel(ChannelDto.of(videoEntity.getChannel()))
                    .videoIdx(videoEntity.getIdx())
                    .videoId(videoEntity.getVideoId())
                    .isOnSale(checkOnSale(videoEntity))
                    .build();
    }
    private static boolean checkOnSale(VideoEntity videoEntity){
        return videoEntity.getExpirationDate().isAfter(LocalDateTime.now());
    }

}