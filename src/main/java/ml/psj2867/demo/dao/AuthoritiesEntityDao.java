package ml.psj2867.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.AuthoritiesEntity;
import ml.psj2867.demo.entity.UserEntity;

@Repository
public interface AuthoritiesEntityDao extends JpaRepository<AuthoritiesEntity, Integer> {
    List<AuthoritiesEntity> findByUserIs(UserEntity user);
}