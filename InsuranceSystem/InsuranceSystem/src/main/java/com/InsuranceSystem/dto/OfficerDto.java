package com.InsuranceSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OfficerDto(
        @NotBlank(message = "username is mandatory")
        String username,
        @NotBlank(message = "password is mandatory")
        String password,
        @NotBlank(message = "name is mandatory")
        String name,
        @NotBlank(message = "email is mandatory")
        String email
) {
}
