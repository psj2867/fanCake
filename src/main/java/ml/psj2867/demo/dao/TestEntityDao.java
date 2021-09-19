package ml.psj2867.demo.dao;

import org.springframework.stereotype.Repository;

import ml.psj2867.demo.entity.TestEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TestEntityDao extends JpaRepository<TestEntity, Integer> {


}