package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.service.trading.model.TradingTypeEnum;

@Entity(name = TradingHistoryEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradingHistoryEntity{
    public final static String ENTITY_NAME = "trading_history";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @NotNull private String channelTitle;
    @NotNull private String channelID;

    @Enumerated(EnumType.STRING)
    @NotNull private TradingTypeEnum type;

    @NotNull private String videoTitle;
    @NotNull private String videoId;
    
    @NotNull private int size;
    @NotNull private double price;
    @NotNull private double userAfterBalance;
    @NotNull private LocalDateTime createdDate;   
    
    @NotNull private String userId; 

    @ManyToOne
    @JoinColumn(name =  "owner_idx")
    @NotNull private UserEntity owner;
    

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }    

}