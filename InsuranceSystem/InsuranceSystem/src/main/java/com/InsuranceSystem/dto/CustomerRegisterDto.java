package com.InsuranceSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CustomerRegisterDto(
        @NotBlank(message = "username is mandatory")
        String username,
        @NotBlank(message = "password is mandatory")
        String password,
        @NotBlank(message = "name is mandatory")
        String name,
        @NotBlank(message = "email is mandatory")
        @Email(message = "email should be valid")
        String email,
        @NotBlank(message = "address is mandatory")
        String address,
        @NotNull(message = "date of birth is mandatory")
        Date dateOfBirth,
        @NotBlank(message = "aadhaar number is mandatory")
        String aadhaarNumber,
        @NotBlank(message = "pan number is mandatory")
        String panNumber,
        @NotBlank(message = "phone number is mandatory")
        String phoneNumber
) {
}
