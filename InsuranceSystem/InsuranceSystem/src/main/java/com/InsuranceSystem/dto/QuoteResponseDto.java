package com.InsuranceSystem.dto;

import java.time.LocalDate;

public record QuoteResponseDto(
        int quoteId,
        double coverageAmount,
        double premiumAmount,
        double baseAmount,
        LocalDate validUntil,
        String quoteStatus,
        int vehicleAge,
        double ageCharge,
        double policyCharge,
        double coverageCharge,
        double addOnCharge
) {
}
