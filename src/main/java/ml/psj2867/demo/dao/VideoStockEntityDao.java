package ml.psj2867.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.VideoStockEntity;

@Repository
public interface VideoStockEntityDao extends JpaRepository<VideoStockEntity, Integer> {

}