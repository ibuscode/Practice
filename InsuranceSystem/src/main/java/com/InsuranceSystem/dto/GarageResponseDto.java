package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.GarageStatus;

public record GarageResponseDto(
        int garageId,
        String garageName,
        String address,
        String city,
        String contactNumber,
        GarageStatus status
) {
}
