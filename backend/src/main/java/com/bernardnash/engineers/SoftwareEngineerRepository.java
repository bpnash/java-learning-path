package com.bernardnash.engineers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Database layer - can use other interfaces like CrudRepository
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Long> {
    List<SoftwareEngineer> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
    List<SoftwareEngineer> findByTechStacksTechStackOrderByNameAsc(TechStackType techStack);
}
