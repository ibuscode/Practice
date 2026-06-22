package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UserProfileDto(
        int userId,
        @NotBlank(message = "username is mandatory")
        String username,
        @NotNull(message = "role is mandatory")
        Role role,
        @NotBlank(message = "name is mandatory")
        String name,
        @NotBlank(message = "email is mandatory")
        @Email(message = "email should be valid")
        String email,
        @NotBlank(message = "address is mandatory")
        String address,
        @NotNull(message = "date of birth is mandatory")
        Date dateOfBirth,

        @NotBlank(message = "phone number is mandatory")
        String phoneNumber
) {
}
