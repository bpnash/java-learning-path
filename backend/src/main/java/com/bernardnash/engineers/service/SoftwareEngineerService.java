package com.bernardnash.engineers.service;

import com.bernardnash.engineers.entity.SoftwareEngineer;
import com.bernardnash.engineers.entity.TechStack;
import com.bernardnash.engineers.exception.SoftwareEngineerNotFoundException;
import com.bernardnash.engineers.model.TechStackType;
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

    public SoftwareEngineerDto getSoftwareEngineerById(Long id) {
        return softwareEngineerRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new SoftwareEngineerNotFoundException(id));
    }

    public SoftwareEngineerDto createSoftwareEngineer(CreateSoftwareEngineerDto createEngineerDto) {
        SoftwareEngineer engineer = new SoftwareEngineer(createEngineerDto.name(), createEngineerDto.email());
        createEngineerDto.techStacks().forEach(techStack -> engineer.addTechStack(new TechStack(techStack)));
        return toDto(softwareEngineerRepository.save(engineer));
    }

    public List<SoftwareEngineerDto> getSoftwareEngineersByName(String name) {
        return softwareEngineerRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(this::toDto)
                .toList();
    }

    public List<SoftwareEngineerDto> getSoftwareEngineersByTechStack(TechStackType techStack) {
        return softwareEngineerRepository.findByTechStacksTechStackOrderByNameAsc(techStack).stream()
                .map(this::toDto)
                .toList();
    }

    private SoftwareEngineerDto toDto(SoftwareEngineer engineer) {
        return new SoftwareEngineerDto(
                engineer.getId(),
                engineer.getName(),
                engineer.getEmail(),
                engineer.getTechStacks().stream().map(TechStack::getTechStack).toList()
        );
    }
}
