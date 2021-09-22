package ml.psj2867.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;

@Repository
public interface UserEntityDao extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByIdIsAndPasswdIsAndLoginTypeIs(String id , String passwd, LoginTypeEnum Type);

    Optional<UserEntity> findByIdIsAndLoginTypeIs(String id , LoginTypeEnum type);
}