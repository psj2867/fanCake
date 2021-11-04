package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.entity.type.VideoAuctionState;

@Entity(name = VideoEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoEntity{
    public final static String ENTITY_NAME = "video";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "VIDEO_ID")
    @NotNull private String videoId;
    @NotNull private String videoTitle;
    private long stockSize;
    private double pricePerShare;    
    private VideoAuctionState auctionState;
    @NotNull private LocalDateTime createdDate;    
    private LocalDateTime expirationDate;

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }
    
    @ManyToOne
    @JoinColumn(name = "CHANNEL_IDX")
    @NotNull private ChannelEntity channel;

    @Formula("( select sum(s.size) from stock s where s.VIDEO_IDX = idx )")
    private Long currentAmount;


    @OneToMany(mappedBy = "video")
    private List<StockEntity> stocks;

    public long getSize(){
        return this.currentAmount == null ? 0 : this.currentAmount;        
    }
    public boolean checkOnSale(){
        VideoAuctionState autctionState = this.getAuctionState();
        if(autctionState != null) return autctionState.isSuccess();
        else if(this.getStockSize() == this.getCurrentAmount() ) return false;        
        return this.getExpirationDate().isAfter(LocalDateTime.now());
    }

}