package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotNull;

public record AssignOfficerRequestDto(
        @NotNull(message = "Proposal ID is required")
        Integer proposalId,

        @NotNull(message = "Officer ID is required")
        Integer officerId
) {
}
