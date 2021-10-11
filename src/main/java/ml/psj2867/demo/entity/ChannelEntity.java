package ml.psj2867.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    private String channnelId;
    private String channelTitle;
    private LocalDateTime createdDate;    

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }
    
    @ManyToOne
    @JoinColumn(name = "owner_idx")
    private UserEntity owner;
    
    @OneToMany(mappedBy = "channel")
    private List<VideoEntity> videos; 

}