package com.bernardnash.engineers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Database layer - can use other interfaces like CrudRepository
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
    List<SoftwareEngineer> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
