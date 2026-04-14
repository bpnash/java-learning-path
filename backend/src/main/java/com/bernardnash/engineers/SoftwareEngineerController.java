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


    @PostMapping
    public void addSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
       softwareEngineerService.createSoftwareEngineer(softwareEngineer);
    }
}
