package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.VehicleType;

import java.util.List;

public record VehicleWithPoliciesDto(
        int vehicleId,
        String vehicleName,
        String regNo,
        int year,
        VehicleType vehicleType,
        int policyCount,
        List<VehiclePolicyDto> policies
) {
}
