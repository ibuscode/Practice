package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.QuoteStatus;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;

public record QuoteResponseDto(
        int quoteId,
        int proposalId,
        double premiumAmount,
        double baseAmount,
        double addonAmount,
        double vehicleAgeAmount,
        double riskAmount,
        @NotNull(message = "valid until is mandatory")
        LocalDate validUntil,
        @NotNull(message = "quote status is mandatory")
        QuoteStatus quoteStatus,
        @NotNull(message = "generated at is mandatory")
        Instant generatedAt
) {
}
