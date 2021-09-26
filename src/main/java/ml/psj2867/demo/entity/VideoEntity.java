package ml.psj2867.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    @GeneratedValue
    private int idx;

    @Column(name = "VIDEO_ID")
    private String videoId;
    private String videoTitle;
    private int stockSize;
    private double pricePerShare;    
    private LocalDateTime expirationDate;
    
    @ManyToOne
    @JoinColumn(name = "CHANNEL_IDX")
    private ChannelEntity channel;


    @OneToMany(mappedBy = "video")
    private List<StockEntity> sotkcs;

}