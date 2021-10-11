package ml.psj2867.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.AdvanceBookingEntity;

@Repository
public interface AdvanceBookingEntityDao extends JpaRepository<AdvanceBookingEntity, Integer> {

}