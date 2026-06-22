package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.GarageStatus;
import jakarta.validation.constraints.NotNull;

public record GarageStatusUpdateDto(
        @NotNull(message = "Status is required")
        GarageStatus status
) {
}
