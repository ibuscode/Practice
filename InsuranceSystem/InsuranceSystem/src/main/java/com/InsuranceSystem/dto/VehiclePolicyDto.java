package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ClaimType;
import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.enums.PolicyType;

public record VehiclePolicyDto(
        String policyNumber,
        PolicyType policyType,
        ClaimType claimType,
        double coverageAmount,
        String expiryDate,
        PolicyStatus policyStatus
) {
}
