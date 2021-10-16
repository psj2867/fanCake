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

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String videoId;
    private String videoTitle;
    private long stockSize;
    private double pricePerShare;    
    private LocalDateTime createdDate;    
    private LocalDateTime expirationDate;

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }
    
    @ManyToOne
    @JoinColumn(name = "CHANNEL_IDX")
    private ChannelEntity channel;

    @Formula("( select sum(stock.size) from stock where stock.idx = idx )")
    private Long size;


    @OneToMany(mappedBy = "video")
    private List<StockEntity> sotkcs;

    public long getSize(){
        return this.size == null ? 0 : this.size;        
    }

}