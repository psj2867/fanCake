package ml.psj2867.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.VideoEntity;

@Repository
public interface VideoEntityDao extends JpaRepository<VideoEntity, Integer> , JpaSpecificationExecutor<VideoEntity> {
    
    

}