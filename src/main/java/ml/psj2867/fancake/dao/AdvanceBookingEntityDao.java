package ml.psj2867.fancake.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.psj2867.fancake.entity.AdvanceBookingEntity;

@Repository
public interface AdvanceBookingEntityDao extends JpaRepository<AdvanceBookingEntity, Integer> {

}