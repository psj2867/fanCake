package ml.psj2867.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = StockEntity.ENTITY_NAME )
@SequenceGenerator(
     name = "stock_seq_gen"
    ,sequenceName = "STOCK_SEQ"
    ,allocationSize = 1
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockEntity{
    public final static String ENTITY_NAME = "stock";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq_gen")
    private int idx;

    @ManyToOne
    @JoinColumn(name = "VIDEO_IDX")
    private VideoEntity video;

    @ManyToOne
    @JoinColumn(name = "OWNER_IDX")
    private UserEntity owner;

    private int size;
    private double price;

}