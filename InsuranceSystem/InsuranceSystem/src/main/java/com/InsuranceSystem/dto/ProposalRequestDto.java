package com.InsuranceSystem.dto;

import com.InsuranceSystem.model.ProposalAddon;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ProposalRequestDto(
        @NotNull(message = "vehicle id is mandatory")
        Integer vehicleId,
        @NotNull(message = "policy type id is mandatory")
        Integer policyTypeId,
        ProposalAddon addon,
        @NotNull(message = "coverage start date is mandatory")
        LocalDate coverageStartDate,
        String remarks
) {
}
