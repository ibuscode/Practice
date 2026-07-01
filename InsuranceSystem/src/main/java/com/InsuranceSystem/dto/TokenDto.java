package com.InsuranceSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenDto(
        @NotBlank(message = "username is mandatory")
        String username,
        @NotBlank(message = "token is mandatory")
        String token
) {
}
