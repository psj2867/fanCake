package ml.psj2867.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = ChannelEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelEntity{
    public final static String ENTITY_NAME = "channel_info";
    @Id
    @GeneratedValue
    private int idx;

    private String channnelId;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;
    
    @OneToMany(mappedBy = "channel")
    private List<VideoEntity> videos; 

}