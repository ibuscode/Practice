package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.AddOn;
import com.InsuranceSystem.enums.PolicyType;
import com.InsuranceSystem.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuoteRequestDto(
        @NotBlank(message = "Registration number is mandatory")
        String regNo,

        @NotNull(message = "Vehicle type is mandatory")
        VehicleType vehicleType,

        @NotBlank(message = "Manufacturer is mandatory")
        String manufacturer,

        @NotBlank(message = "Model is mandatory")
        String model,

        @NotNull(message = "Manufacturing year is mandatory")
        int manufacturingYear,

        @NotNull(message = "Policy type is mandatory")
        PolicyType policyType,

        @NotNull(message = "Coverage amount is mandatory")
        double coverageAmount,
        List<AddOn> addOns
)
 {
}
