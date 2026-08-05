package com.bernardnash.engineers;

import org.springframework.stereotype.Service;

import java.util.List;

// This class handles the business logic i.e. the SQL Queries
// **NOTE** don't forget the validations which go in here
@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;

    // Constructor
    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }

    public SoftwareEngineer getSoftwareEngineerById(Long id) {
        return softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(id + " not found"));
    }

    public List<SoftwareEngineer> getSoftwareEngineersByName(String name) {
        return softwareEngineerRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name);
    }

    public List<SoftwareEngineer> getSoftwareEngineersByTechStack(TechStackType techStack) {
        return softwareEngineerRepository.findByTechStacksTechStackOrderByNameAsc(techStack);
    }

    // Use a DTO in a real app
    public SoftwareEngineer createSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        // re-link children so the FK is populated
        softwareEngineer.setTechStacks(softwareEngineer.getTechStacks());
        return softwareEngineerRepository.save(softwareEngineer);
    }
}
