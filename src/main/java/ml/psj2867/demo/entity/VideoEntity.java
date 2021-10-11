package ml.psj2867.demo.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    private int stockSize;
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

    @OneToMany(mappedBy = "video")
    private List<StockEntity> sotkcs;


    public int getCurrentStockSize(EntityManager em){
        int videoIdx = this.idx;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> q = cb.createQuery(Number.class);
        Root<StockEntity> stock= q.from(StockEntity.class);

        q.select(cb.sum(stock.get("size")).alias("sum"));
        q.where(cb.equal(stock.get("video"), videoIdx));
        int count = Optional.ofNullable( em.createQuery(q).getSingleResult() )
                        .map(Number::intValue)
                        .orElse(0);
        return count;
    }

}