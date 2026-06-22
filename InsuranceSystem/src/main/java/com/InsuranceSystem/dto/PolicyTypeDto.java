package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.CoverageType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PolicyTypeDto(
        int id,
        @NotBlank(message = "policy name is mandatory")
        String policyName,
        @NotBlank(message = "description is mandatory")
        String description,
        double basePremium,
        @NotNull(message = "coverage type is mandatory")
        CoverageType coverageType,
        boolean active,

        List<AddOnDto> addOns
) {
}
