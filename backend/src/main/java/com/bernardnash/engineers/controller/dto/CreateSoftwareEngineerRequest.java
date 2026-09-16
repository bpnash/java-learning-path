package com.bernardnash.engineers.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.bernardnash.engineers.model.TechStackType;
import java.util.List;

public record CreateSoftwareEngineerRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 255) String email,
        List<TechStackType> techStacks
) {
}
