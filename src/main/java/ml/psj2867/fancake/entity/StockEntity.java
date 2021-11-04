package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
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

@Entity(name = StockEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockEntity{
    public final static String ENTITY_NAME = "stock";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @NotNull private double price;
    @NotNull private int size;
    @NotNull private LocalDateTime createdDate;    
    
    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name="OWNER_IDX")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name="VIDEO_IDX")
    private VideoEntity video;
    
}