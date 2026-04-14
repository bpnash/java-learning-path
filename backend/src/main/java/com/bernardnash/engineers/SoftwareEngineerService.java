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
        // Many methods to choose here
        // This writes the SQL behind the scenes
        return softwareEngineerRepository.findAll();
    }

   

    // Use a DTO in a real app
    public void createSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        softwareEngineerRepository.save(softwareEngineer);
    }

    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(id + " not found"));
    }
}
