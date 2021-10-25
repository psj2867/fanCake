package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.configure.security.AuthEnum;
import ml.psj2867.fancake.entity.embedded.AddressEmbedded;
import ml.psj2867.fancake.entity.embedded.BankEmbedded;
import ml.psj2867.fancake.entity.embedded.TermsEmbedded;
import ml.psj2867.fancake.service.user.model.auth.LoginTypeEnum;

@Entity(name = UserEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
     @UniqueConstraint( columnNames = {"id","loginType"})
    // ,@UniqueConstraint( columnNames = {"phoneNumber"})
    // ,@UniqueConstraint( columnNames = {"email"})
})
public class UserEntity{
    public final static String ENTITY_NAME = "user_info";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    @ColumnDefault(value = "0")
    @Builder.Default
    private double balance = 0;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    private LoginTypeEnum loginType;

    @Embedded
    private BankEmbedded bank;
    @Embedded
    private AddressEmbedded address;
    @Embedded
    private TermsEmbedded terms;
    
    @OneToMany(mappedBy = "user" )
    private List<AuthoritiesEntity> auths;
    @OneToMany(mappedBy = "owner")
    private List<StockEntity> stocks;
    @OneToMany(mappedBy = "owner")    
    private List<ChannelEntity> channel;
    @OneToMany(mappedBy = "owner")    
    private List<TradingHistoryEntity> trading;

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
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
}