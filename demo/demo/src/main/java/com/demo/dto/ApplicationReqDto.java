package com.demo.dto;

import jakarta.validation.constraints.NotNull;

public record ApplicationReqDto(
        @NotNull(message = "Job ID is required")
        Integer jobId
) {
}
