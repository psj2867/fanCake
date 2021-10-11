package ml.psj2867.fancake.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.user.model.auth.LoginTypeEnum;

@Repository
public interface UserEntityDao extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByIdIsAndPasswordIsAndLoginTypeIs(String id , String passwd, LoginTypeEnum Type);

    Optional<UserEntity> findByIdIsAndLoginTypeIs(String id , LoginTypeEnum type);

    Optional<UserEntity> findByNameIsAndEmailIsAndLoginTypeIs(String id , String email, LoginTypeEnum Type);
}