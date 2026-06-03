package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ProposalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record ProposalResponseDto(
        int proposalId,
        @NotNull(message = "proposal status is mandatory")
        ProposalStatus proposalStatus,
        String remarks,
        String requestDetails,
        @NotNull(message = "coverage start date is mandatory")
        LocalDate coverageStartDate,
        @NotNull(message = "submitted at is mandatory")
        Instant submittedAt,
        @NotNull(message = "updated at is mandatory")
        Instant updatedAt,
        @NotNull(message = "user is mandatory")
        UserProfileDto user,
        @NotNull(message = "vehicle is mandatory")
        VehicleResDto vehicle,

        @NotNull(message = "policy type is mandatory")
        PolicyTypeDto policyType

) {
}
