package com.bernardnash.engineers.controller.dto;

import com.bernardnash.engineers.model.TechStackType;

import java.util.List;

public class SoftwareEngineerResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final List<TechStackType> techStacks;

    public SoftwareEngineerResponse(Long id, String name, String email, List<TechStackType> techStacks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.techStacks = List.copyOf(techStacks);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<TechStackType> getTechStacks() {
        return techStacks;
    }
}
