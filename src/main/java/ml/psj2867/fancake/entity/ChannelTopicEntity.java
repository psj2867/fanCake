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
import ml.psj2867.fancake.entity.type.VideoAuctionState;

@Entity(name = ChannelTopicEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelTopicEntity{
    public final static String ENTITY_NAME = "channel_topic";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    private String topic;

    @ManyToOne
    @JoinColumn(name = "CHANNEL_IDX")
    private ChannelEntity channel;


}