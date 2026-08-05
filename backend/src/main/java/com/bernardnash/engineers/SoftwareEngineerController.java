package com.bernardnash.engineers;

import org.springframework.web.bind.annotation.*;

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
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
    }

    @GetMapping("{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Long id) {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    @GetMapping(params = "name")
    public List<SoftwareEngineer> getEngineersByName(@RequestParam String name) {
        return softwareEngineerService.getSoftwareEngineersByName(name);
    }

    @GetMapping(params = "techStack")
    public List<SoftwareEngineer> getEngineersByTechStack(@RequestParam TechStackType techStack) {
        return softwareEngineerService.getSoftwareEngineersByTechStack(techStack);
    }

    @PostMapping
    public SoftwareEngineer addSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
        return softwareEngineerService.createSoftwareEngineer(softwareEngineer);
    }
}
