package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.ClaimType;

import java.time.Instant;
import java.time.LocalDate;

public record ClaimResponseDto(
        int claimId,
        Long policyId,
        String policyNumber,
        ClaimType claimType,
        ClaimStatus claimStatus,
        String claimReason,
        Double claimAmount,
        LocalDate incidentDate,
        String officerRemarks,
        String garageName,
        String garageAddress,
        Double repairBillAmount,
        String receiptPath,
        Instant createdAt,
        String customerName,
        String officerName
) {
}
