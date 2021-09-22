package ml.psj2867.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;

@Entity(name = UserEntity.ENTITY_NAME )
@SequenceGenerator(
     name =  "user_seg_gen"
    ,sequenceName = "USER_SEQ"
    ,allocationSize = 1
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity{
    public final static String ENTITY_NAME = "user";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "user_seq_gen")
    private int idx;

    private String id;
    private String passwd;
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "LOGIN_TYPE")
    private LoginTypeEnum loginType;
    
    @OneToMany(mappedBy = "user" )
    private List<AuthoritiesEntity> auths;
    @OneToMany(mappedBy = "owner")
    private List<StockEntity> stocks; 
    @OneToMany(mappedBy = "owner")
    private List<VideoEntity> videos; 

}