package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.VehicleType;

public record VehicleResponseDto(
        int vehicleId,
        VehicleType vehicleType,
        String manufacturer,
        String model,
        int year,
        String regNo,
        String customerName
) {
}
