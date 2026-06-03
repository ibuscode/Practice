package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotNull;

public record SendQuoteRequestDto(
        @NotNull
        Long quoteId
) {
}
