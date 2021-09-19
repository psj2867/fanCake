package ml.psj2867.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity{
    @Id
    @GeneratedValue
    private int id;

    private String name;
}