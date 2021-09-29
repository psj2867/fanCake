package ml.psj2867.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;

@Entity(name = UserEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity{
    public final static String ENTITY_NAME = "user_info";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String id;
    private String passwd;
    private String name;
    private LocalDateTime createdDate;    
    
    @PrePersist
    public void saveAt(){
        this.createdDate = LocalDateTime.now();
    }
    
    @Enumerated(EnumType.STRING)
    private LoginTypeEnum loginType;
    
    @Builder.Default
    private boolean isCreator = false;
    
    @OneToMany(mappedBy = "user" )
    private List<AuthoritiesEntity> auths;
    @OneToMany(mappedBy = "owner")
    private List<StockEntity> stocks;
    @OneToMany(mappedBy = "owner")    
    private List<ChannelEntity> channel;
}