package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "username is mandatory")
        String username,
        @NotBlank(message = "password is mandatory")
        String password
) {
}
