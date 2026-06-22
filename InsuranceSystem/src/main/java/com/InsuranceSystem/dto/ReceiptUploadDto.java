package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotNull;

public record ReceiptUploadDto(
        @NotNull(message = "Claim ID is required")
        int claimId
) {
}
