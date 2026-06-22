package com.InsuranceSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OfficerDto(
        @NotNull
        @NotBlank(message = "name is mandatory")
        @Size(min = 4, message = "Name should be at-least 4 characters")
        String name,
        @NotNull
        @NotBlank(message = "username is mandatory")
        @Size(min = 4, message = "UserName should be at-least 4 characters")
        String username

) {
}
