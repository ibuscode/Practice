package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.ClaimResponseDto;
import com.InsuranceSystem.dto.ClaimSummaryDto;
import com.InsuranceSystem.model.Claim;

public class ClaimMapper {

    public static ClaimResponseDto claimToDto(Claim claim) {
        return new ClaimResponseDto(
                claim.getClaimId(),
                claim.getPolicy().getPolicyId(),
                claim.getClaimDate(),
                claim.getIncidentDate(),
                claim.getDescription(),
                claim.getClaimedAmount(),
                claim.getApprovedAmount(),
                claim.getClaimStatus(),
                claim.getUpdatedAt()
        );
    }

    public static ClaimSummaryDto claimToSummaryDto(Claim claim) {
        return new ClaimSummaryDto(
                claim.getClaimId(),
                claim.getClaimStatus(),
                claim.getClaimedAmount(),
                claim.getApprovedAmount()
        );
    }
}
