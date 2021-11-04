package ml.psj2867.fancake.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = AdvanceBookingEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvanceBookingEntity{
    public final static String ENTITY_NAME = "advance_resevation";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @NotNull private String channelUrl;
    @NotNull private String email;

    @NotNull private LocalDateTime createdDate;    

    @PrePersist
    private void saveAt(){
        if(this.createdDate == null)
            this.createdDate = LocalDateTime.now();
    }



}