package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.configure.security.AuthEnum;
import ml.psj2867.fancake.dao.UserDetailEntityDao;
import ml.psj2867.fancake.dao.UserEntityDao;
import ml.psj2867.fancake.service.oauth.model.LoginTypeEnum;

@Entity(name = UserEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
     @UniqueConstraint( columnNames = {"id","loginType"})
    ,@UniqueConstraint( columnNames = {"email"})
})
public class UserEntity{
    public final static String ENTITY_NAME = "user_info";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    
    @NotNull private String id;
    @Builder.Default
    @NotNull private String password="";
             private String temp_origin_password;
    @NotNull private String name;
             private String phoneNumber;
    @NotNull private String email;
    @ColumnDefault(value = "0")
    @Builder.Default
    @NotNull private double balance = 0;
    @NotNull private LocalDateTime createdDate;
    @NotNull private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @NotNull private LoginTypeEnum loginType;

    
    @OneToMany(mappedBy = "user" )
    private List<AuthoritiesEntity> auths;
    @OneToMany(mappedBy = "owner")
    private List<StockEntity> stocks;
    @OneToMany(mappedBy = "owner")    
    private List<ChannelEntity> channel;
    @OneToMany(mappedBy = "owner")    
    private List<TradingHistoryEntity> trading;
    @OneToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "detail_idx")
    @NotNull private UserDetailEntity detail;

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
        if(this.updatedDate == null)
            this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    private void updatePre(){
        this.updatedDate = LocalDateTime.now();
    }

    public List<GrantedAuthority> getGrants(){
        if(this.getAuths() == null || this.getAuths().isEmpty())
            return Arrays.asList(AuthEnum.USER);
        return this.getAuths().stream()
                            .map(AuthoritiesEntity::getAuth)
                            .collect(Collectors.toList());
    }
    public boolean isValidPassword(String password, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(password, this.getPassword());
    }

    public void save(UserEntityDao userDao, UserDetailEntityDao userDetailDao){
        if(this.getDetail() == null)
            this.setDetail(new UserDetailEntity());
        userDetailDao.save(this.getDetail());
        userDao.save(this);
    }

    public void delete(UserEntityDao userDao, UserDetailEntityDao userDetailDao){
        resetDetail(userDetailDao);
        this.setEmail(this.getIdx().toString());
        this.setId(this.getIdx().toString());
        this.setName(this.getIdx().toString());
        this.setPassword(this.getIdx().toString());
        this.setPhoneNumber(this.getIdx().toString());
        this.setLoginType(LoginTypeEnum.DELETED);
        userDao.save(this);
    }
    private void resetDetail(UserDetailEntityDao userDetailDao){
        UserDetailEntity detail =  this.getDetail();
        this.setDetail(UserDetailEntity.defaultDeletedUserDetail(userDetailDao));
        userDetailDao.delete(detail);
    }
}