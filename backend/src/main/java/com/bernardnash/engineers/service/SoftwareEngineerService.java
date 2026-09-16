package com.bernardnash.engineers.service;

import com.bernardnash.engineers.entity.SoftwareEngineer;
import com.bernardnash.engineers.exception.SoftwareEngineerNotFoundException;
import com.bernardnash.engineers.repository.SoftwareEngineerRepository;
import com.bernardnash.engineers.service.dto.CreateSoftwareEngineerDto;
import com.bernardnash.engineers.service.dto.SoftwareEngineerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public List<SoftwareEngineerDto> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public SoftwareEngineerDto getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new SoftwareEngineerNotFoundException(id));
    }

    public SoftwareEngineerDto createSoftwareEngineer(CreateSoftwareEngineerDto createEngineerDto) {
        SoftwareEngineer engineer = new SoftwareEngineer(createEngineerDto.name(), createEngineerDto.techStack());
        return toDto(softwareEngineerRepository.save(engineer));
    }

    private SoftwareEngineerDto toDto(SoftwareEngineer engineer) {
        return new SoftwareEngineerDto(engineer.getId(), engineer.getName(), engineer.getTechStack());
    }
}
