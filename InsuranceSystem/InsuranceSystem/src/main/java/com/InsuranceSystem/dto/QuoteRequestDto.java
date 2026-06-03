package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotNull;

public record QuoteRequestDto(
        @NotNull(message = "proposal id is mandatory")
        Integer proposalId
) {
}
