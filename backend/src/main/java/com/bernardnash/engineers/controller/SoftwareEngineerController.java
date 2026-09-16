package com.bernardnash.engineers.controller;

import com.bernardnash.engineers.controller.dto.CreateSoftwareEngineerRequest;
import com.bernardnash.engineers.controller.dto.SoftwareEngineerResponse;
import com.bernardnash.engineers.service.SoftwareEngineerService;
import com.bernardnash.engineers.service.dto.CreateSoftwareEngineerDto;
import com.bernardnash.engineers.service.dto.SoftwareEngineerDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping
    public List<SoftwareEngineerResponse> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("{id}")
    public SoftwareEngineerResponse getEngineerById(@PathVariable Integer id) {
        return toResponse(softwareEngineerService.getSoftwareEngineerById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SoftwareEngineerResponse addSoftwareEngineer(@Valid @RequestBody CreateSoftwareEngineerRequest request) {
        CreateSoftwareEngineerDto createEngineerDto = new CreateSoftwareEngineerDto(
                request.name(),
                request.techStack()
        );
        return toResponse(softwareEngineerService.createSoftwareEngineer(createEngineerDto));
    }

    private SoftwareEngineerResponse toResponse(SoftwareEngineerDto engineer) {
        return new SoftwareEngineerResponse(engineer.id(), engineer.name(), engineer.techStack());
    }
}
