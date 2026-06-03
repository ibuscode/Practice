package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ClaimStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;

public record ClaimResponseDto(
        int claimId,
        int policyId,
        @NotNull(message = "claim date is mandatory")
        LocalDate claimDate,
        @NotNull(message = "incident date is mandatory")
        LocalDate incidentDate,
        @NotBlank(message = "description is mandatory")
        String description,
        double claimedAmount,
        double approvedAmount,
        @NotNull(message = "claim status is mandatory")
        ClaimStatus claimStatus,
        @NotNull(message = "updated at is mandatory")
        Instant updatedAt
) {
}
