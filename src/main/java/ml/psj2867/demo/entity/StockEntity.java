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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;

@Entity(name = StockEntity.ENTITY_NAME )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockEntity{
    public final static String ENTITY_NAME = "stock";
    @Id
    @GeneratedValue
    private int idx;

    private double price;
    private int size;

    @ManyToOne
    @JoinColumn(name="OWNER_IDX")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name="VIDEO_IDX")
    private VideoEntity video;
    
}