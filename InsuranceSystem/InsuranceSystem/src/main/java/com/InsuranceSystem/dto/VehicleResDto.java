package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleResDto(
        @NotNull(message = "vehicle type is mandatory")
        VehicleType vehicleType,
        @NotBlank(message = "model is mandatory")
        String model,
        @NotNull(message = "year is mandatory")
        Integer year,
        @NotBlank(message = "registration number is mandatory")
        String regNo,
        @NotNull(message = "age of vehicle is mandatory")
        Integer ageOfVehicle
) {
}
