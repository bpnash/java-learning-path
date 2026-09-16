package com.bernardnash.engineers.service.dto;

import com.bernardnash.engineers.model.TechStackType;

import java.util.List;

public record SoftwareEngineerDto(
        Long id,
        String name,
        String email,
        List<TechStackType> techStacks
) {
}
