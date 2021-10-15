package ml.psj2867.fancake.service.stock.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.fancake.entity.StockEntity;
import ml.psj2867.fancake.service.video.model.VideoDto;

@Getter
@Setter
@Builder
public class StockDto {

    private VideoDto video;
    private double price;
    private int size;
    private LocalDateTime createdDate; 
    private boolean isOnSale;

    
    public static StockDto of(StockEntity stockEntity) {
        return StockDto.builder()
                        .video(VideoDto.of(stockEntity.getVideo()))  
                        .price(stockEntity.getPrice())
                        .size(stockEntity.getSize())
                        .createdDate(stockEntity.getCreatedDate())
                        .build();

    }
  
}