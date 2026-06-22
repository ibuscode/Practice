package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateDto(
        @NotBlank(message = "Name is required")
        String name,

        String email,

        String address,

        @NotBlank(message = "Phone number is required")
        String phoneNumber
) {
}
