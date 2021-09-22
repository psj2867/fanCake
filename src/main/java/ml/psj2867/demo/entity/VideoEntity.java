package ml.psj2867.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = VideoEntity.ENTITY_NAME )
@SequenceGenerator(
     name = "video_seq_gen"
    ,sequenceName = "VIDEO_SEQ"
    ,allocationSize = 1
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoEntity{
    public final static String ENTITY_NAME = "video";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_seq_gen")
    private int idx;

    @ManyToOne
    @JoinColumn(name = "OWNER_IDX")
    private UserEntity owner;

    @Column(name = "VIDEO_ID")
    private String videoId;

    @OneToOne(mappedBy = "video")
    private VideoStockEntity videoStockEntity;

}