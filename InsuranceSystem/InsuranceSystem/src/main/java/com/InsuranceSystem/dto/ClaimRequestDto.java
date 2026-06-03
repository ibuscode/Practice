package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClaimRequestDto(
        @NotNull(message = "policy id is mandatory")
        Integer policyId,
        @NotBlank(message = "description is mandatory")
        String description,
        double claimedAmount,
        @NotNull(message = "incident date is mandatory")
        LocalDate incidentDate
) {
}
