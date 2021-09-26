package ml.psj2867.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = AuthoritiesEntity.ENTITY_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthoritiesEntity{
    public final static String ENTITY_NAME = "authorities";
    @Id
    @GeneratedValue
    private int idx;    

    @ManyToOne
    @JoinColumn(name = "user_idx"  )
    private UserEntity user;

    private String auth;
}