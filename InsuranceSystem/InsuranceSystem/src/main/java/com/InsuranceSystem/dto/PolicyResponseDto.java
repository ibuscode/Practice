package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.PolicyStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record PolicyResponseDto(
        int policyId,
        @NotBlank(message = "policy number is mandatory")
        String policyNumber,
        @NotNull(message = "start date is mandatory")
        LocalDate startDate,
        @NotNull(message = "end date is mandatory")
        LocalDate endDate,
        @NotNull(message = "policy status is mandatory")
        PolicyStatus policyStatus,
        long daysLeft,
        double premiumPaid,
        @NotNull(message = "issued at is mandatory")
        Instant issuedAt,

        @NotNull(message = "user is mandatory")
        UserProfileDto user,

        @NotNull(message = "vehicle is mandatory")
        VehicleResDto vehicle,

        @NotNull(message = "policy type is mandatory")
        PolicyTypeDto policyType,


) {
}
