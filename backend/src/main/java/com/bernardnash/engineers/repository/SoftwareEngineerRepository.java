package com.bernardnash.engineers.repository;

import com.bernardnash.engineers.entity.SoftwareEngineer;
import com.bernardnash.engineers.model.TechStackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Long> {
    List<SoftwareEngineer> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
    List<SoftwareEngineer> findByTechStacksTechStackOrderByNameAsc(TechStackType techStack);
}
