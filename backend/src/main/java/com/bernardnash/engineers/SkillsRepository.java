package com.bernardnash.engineers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// Database layer - can use other interfaces like CrudRepository
public interface SkillsRepository extends JpaRepository<EngineerSkillEntity, Long> {
    List<EngineerSkillEntity> findByEngineerId(Long engineerId);
}
