package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.PolicyStatus;

import java.time.Instant;
import java.time.LocalDate;

public record PolicyResponseDto(
        int policyId,
        String policyNumber,
        LocalDate startDate,
        LocalDate endDate,
        PolicyStatus policyStatus,
        Instant issuedAt,
        int proposalId,
        String customerName,
        String vehicleNumber,
        double coverageAmount,
        double premiumAmount
) {
}
