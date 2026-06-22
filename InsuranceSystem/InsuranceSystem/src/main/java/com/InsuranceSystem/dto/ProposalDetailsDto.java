package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ProposalStatus;

public record ProposalDetailsDto(
        int proposalId,
        String customerName,
        String vehicleType,
        String manufacturer,
        String model,
        String regNo,
        double coverageAmount,
        double premiumAmount,
        ProposalStatus proposalStatus,
        String rcBookPath,
        String drivingLicensePath
) {
}
