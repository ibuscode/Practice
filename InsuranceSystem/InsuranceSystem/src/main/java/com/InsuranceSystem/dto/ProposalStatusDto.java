package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record ProposalStatusDto(
        @NotBlank(message = "remarks are mandatory")
        String remarks,
        @NotBlank(message = "request details are mandatory")
        String requestDetails
) {
}
