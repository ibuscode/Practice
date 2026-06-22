package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDto(
        int id,
        @NotBlank(message = "username is mandatory")
        String username,
        @NotBlank(message = "role is mandatory")
        String role
) {
}
