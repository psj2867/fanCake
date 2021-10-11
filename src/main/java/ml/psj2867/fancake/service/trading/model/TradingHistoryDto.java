package ml.psj2867.fancake.service.trading.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.entity.TradingHistoryEntity;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TradingHistoryDto{

    private String channelTitle;
    private String channelID;

    private TradingTypeEnum type;

    private String videoTitle;
    private int size;
    private double price;
    private double userBalance;
    private LocalDateTime createdDate;    

    public static TradingHistoryDto of(TradingHistoryEntity entity){
        return TradingHistoryDto.builder()
                                .channelTitle(entity.getChannelTitle())
                                .channelID(entity.getChannelID())
                                .type(entity.getType())
                                .videoTitle(entity.getVideoTitle())
                                .size(entity.getSize())
                                .price(entity.getPrice())
                                .userBalance(entity.getUserBalance())
                                .createdDate(entity.getCreatedDate())
                                .build();
    }

}