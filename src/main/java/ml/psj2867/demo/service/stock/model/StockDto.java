package ml.psj2867.demo.service.stock.model;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ml.psj2867.demo.entity.StockEntity;
import ml.psj2867.demo.service.video.model.VideoDto;

@Getter
@Setter
@Builder
public class StockDto {

    private VideoDto video;
    private double price;
    private int size;
    private LocalDateTime createdDate; 
    private boolean isOnSale;

    
    public static StockDto of(StockEntity stockEntity, EntityManager em) {
        return StockDto.builder()
                        .video(VideoDto.of(stockEntity.getVideo(),em))  
                        .price(stockEntity.getPrice())
                        .size(stockEntity.getSize())
                        .createdDate(stockEntity.getCreatedDate())
                        .build();

    }
  
}