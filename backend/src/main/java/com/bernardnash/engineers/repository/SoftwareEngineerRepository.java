package com.bernardnash.engineers.repository;

import com.bernardnash.engineers.entity.SoftwareEngineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
}
