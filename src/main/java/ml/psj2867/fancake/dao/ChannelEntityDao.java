package ml.psj2867.fancake.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ml.psj2867.fancake.entity.ChannelEntity;

@Repository
public interface ChannelEntityDao extends JpaRepository<ChannelEntity, Integer>, JpaSpecificationExecutor<ChannelEntity>  {

}