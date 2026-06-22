package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record AddOnDto(
        int addonId,
        @NotBlank(message = "addon name is mandatory")
        String addonName,
        @NotBlank(message = "description is mandatory")
        String description,
        double price
) {
}
