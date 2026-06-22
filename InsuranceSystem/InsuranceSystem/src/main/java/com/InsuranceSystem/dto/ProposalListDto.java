package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ProposalStatus;

public record ProposalListDto(
        int proposalId,
        String customerName,
        String vehicleType,
        double premiumAmount,
        ProposalStatus proposalStatus,
        String officerName

) {
}
