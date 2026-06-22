package com.InsuranceSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CustomerRegisterDto(
        @NotBlank(message = "name is mandatory")
        String name,
        @NotBlank(message = "username is mandatory")
        String username,
        @NotBlank(message = "password is mandatory")
        String password,
        @NotBlank(message = "email is mandatory")
        String email,
        @NotBlank(message = "address is mandatory")
        String address,
        @NotNull(message = "date of birth is mandatory")
        Date dateOfBirth,
        @NotBlank(message = "phone number is mandatory")
        String phoneNumber
) {
}
