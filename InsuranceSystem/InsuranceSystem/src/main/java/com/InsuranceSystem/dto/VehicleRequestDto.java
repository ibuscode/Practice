package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestDto(
        @NotNull(message = "Vehicle type is mandatory")
        VehicleType vehicleType,

        @NotBlank(message = "Manufacturer is mandatory")
        String manufacturer,

        @NotBlank(message = "Model is mandatory")
        String model,

        int year,

        String regNo

) {
}
