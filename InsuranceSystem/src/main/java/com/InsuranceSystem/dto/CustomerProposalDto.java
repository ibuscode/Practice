package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ProposalStatus;

public record CustomerProposalDto(
        int proposalId,
        String vehicle,
        double premiumAmount,
        ProposalStatus proposalStatus,
        String paymentStatus
) {
}
