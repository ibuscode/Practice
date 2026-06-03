package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ClaimStatus;
import jakarta.validation.constraints.NotNull;

public record ClaimSummaryDto(
        int claimId,
        @NotNull(message = "claim status is mandatory")
        ClaimStatus claimStatus,
        double claimedAmount,
        double approvedAmount
) {
}
