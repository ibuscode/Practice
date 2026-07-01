package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ClaimType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClaimRequestDto(
        @NotNull(message = "Policy ID is required")
        Long policyId,

        @NotNull(message = "Claim type is required")
        ClaimType claimType,

        String claimReason,

        @NotNull(message = "Claim amount is required")
        Double claimAmount,

        @NotNull(message = "Incident date is required")
        LocalDate incidentDate,

        // CASHLESS fields
        Integer garageId,

        // REIMBURSEMENT fields
        Double repairBillAmount,
        String receiptPath
) {
}
