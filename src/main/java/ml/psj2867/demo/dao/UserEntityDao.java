package ml.psj2867.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.UserEntity;

@Repository
public interface UserEntityDao extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByIdIsAndPasswdIsAndTypeIs(String id , String passwd, String Type);

    Optional<UserEntity> findByIdIsAndTypeIs(String id , String type);
}