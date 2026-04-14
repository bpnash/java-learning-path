package com.bernardnash.engineers;

import org.springframework.data.jpa.repository.JpaRepository;

// Database layer - can use other interfaces like CrudRepository
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
}
