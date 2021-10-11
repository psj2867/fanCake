package ml.psj2867.fancake.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ml.psj2867.fancake.entity.StockEntity;

@Repository
public interface StockEntityDao extends JpaRepository<StockEntity, Integer> , JpaSpecificationExecutor<StockEntity> {

}