package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ProposalStatus;

public record AssignedProposalDto(
        int proposalId,
        String customerName,
        String vehicle,
        ProposalStatus proposalStatus
) {
}
