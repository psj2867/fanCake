package ml.psj2867.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@SequenceGenerator(
     name = "USER_SEQ_GEN"
    ,sequenceName = "USER_SEQ"
    ,allocationSize = 1
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
    private int idx;

    private String id;
    private String passwd;
    private String name;
    private String type;

}