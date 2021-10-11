package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.configure.security.AuthEnum;

@Entity(name = AuthoritiesEntity.ENTITY_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthoritiesEntity{
    public final static String ENTITY_NAME = "authorities";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;    
    
    @Enumerated(EnumType.STRING)
    private AuthEnum auth;
    private LocalDateTime createdDate;    

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_idx"  )
    private UserEntity user;

}