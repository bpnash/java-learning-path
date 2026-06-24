package com.bernardnash.engineers;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;
    private final SkillsService skillsService;

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService, SkillsService skillsService) {
        this.softwareEngineerService = softwareEngineerService;
        this.skillsService = skillsService;
    }

    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
//        Hard coded data
//        return List.of(
//                new SoftwareEngineer(1, "james", "js, node, react, tailwind-css"),
//                new SoftwareEngineer(2, "jamila", "java, spring, spring boot")
//       );
    }

    @GetMapping("{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Integer id) {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    @GetMapping(params = "name")
    public List<SoftwareEngineer> getEngineersByName(@RequestParam String name) {
        return softwareEngineerService.getSoftwareEngineersByName(name);
    }

    @GetMapping(params = "techStack")
    public List<SoftwareEngineer> getEngineersByTechStack(@RequestParam String techStack) {
        // Implement this method in the service and repository layers
        return softwareEngineerService.getSoftwareEngineersByTechStack(techStack);
    }
    @PostMapping
    public void addSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
       softwareEngineerService.createSoftwareEngineer(softwareEngineer);
    }

    @PostMapping
    public void addSkillByEngineerId(@RequestBody SkillRequest skillRequest) {
        skillsService.addSkillToEngineer(skillRequest.getEngineerId(), skillRequest.getSkillName());
    }
}
