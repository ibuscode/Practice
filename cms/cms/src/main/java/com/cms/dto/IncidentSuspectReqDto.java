package com.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidentSuspectReqDto(
        @NotBlank
        @NotNull
        @Size(max = 500)
        String details
) {
}
