package com.InsuranceSystem.dto;

public record CustomerProfileDto(
        String name,
        String email,
        String address,
        String phoneNumber,
        String role
) {
}
