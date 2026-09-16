package com.bernardnash.engineers.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSoftwareEngineerRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 255) String techStack
) {
}
