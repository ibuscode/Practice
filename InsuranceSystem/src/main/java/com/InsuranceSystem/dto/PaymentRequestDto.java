package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentRequestDto(
        @NotNull(message = "Proposal ID is required")
        Integer proposalId


) {
}
