package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record GarageRequestDto(
        @NotBlank(message = "Garage name is required")
        String garageName,

        @NotBlank(message = "Address is required")
        String address,

        String city,

        String contactNumber
) {
}
