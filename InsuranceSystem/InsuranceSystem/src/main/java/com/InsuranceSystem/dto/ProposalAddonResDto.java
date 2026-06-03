package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record ProposalAddonResDto(

        @NotBlank()
        String remarks
) {
}
