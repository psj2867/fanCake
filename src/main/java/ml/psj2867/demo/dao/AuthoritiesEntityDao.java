package ml.psj2867.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.configure.security.AuthEnum;
import ml.psj2867.demo.entity.AuthoritiesEntity;
import ml.psj2867.demo.entity.UserEntity;

@Repository
public interface AuthoritiesEntityDao extends JpaRepository<AuthoritiesEntity, Integer> , JpaSpecificationExecutor<AuthoritiesEntity>{
    List<AuthoritiesEntity> findByUserIs(UserEntity user);
    Optional<AuthoritiesEntity> findByUserIsAndAuthIs(UserEntity user, AuthEnum auth);
}