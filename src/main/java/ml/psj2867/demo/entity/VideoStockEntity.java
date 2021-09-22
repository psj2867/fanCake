package ml.psj2867.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = VideoStockEntity.ENTITY_NAME )
@SequenceGenerator(
     name = "video_sotck_seq_gen"
    ,sequenceName = "VIDEO_SOTCK_SEQ"
    ,allocationSize = 1
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoStockEntity{
    public final static String ENTITY_NAME = "video_stock";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_sotck_seq_gen")
    private int idx;

    private int stock_size;

    @OneToOne
    @JoinColumn(name="VIDEO_IDX")
    private VideoEntity video;

}