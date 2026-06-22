package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.ClaimRequestDto;
import com.InsuranceSystem.dto.ClaimResponseDto;
import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.ClaimType;
import com.InsuranceSystem.model.Claim;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Garage;
import com.InsuranceSystem.model.Policy;

public class ClaimMapper {

    public static Claim toEntity(ClaimRequestDto request, Policy policy, Customer customer, Garage garage) {
        Claim claim = new Claim();
        claim.setPolicy(policy);
        claim.setCustomer(customer);
        claim.setClaimType(request.claimType());
        claim.setClaimReason(request.claimReason());
        claim.setClaimAmount(request.claimAmount());
        claim.setIncidentDate(request.incidentDate());
        claim.setClaimStatus(ClaimStatus.SUBMITTED);

        // CASHLESS fields
        if (request.claimType() == ClaimType.CASHLESS) {
            claim.setGarage(garage);
        }

        // REIMBURSEMENT fields
        claim.setRepairBillAmount(request.repairBillAmount());
        claim.setReceiptPath(request.receiptPath());

        return claim;
    }

    public static ClaimResponseDto toDto(Claim claim) {
        return new ClaimResponseDto(
                claim.getClaimId(),
                (long) claim.getPolicy().getPolicyId(),
                claim.getPolicy().getPolicyNumber(),
                claim.getClaimType(),
                claim.getClaimStatus(),
                claim.getClaimReason(),
                claim.getClaimAmount(),
                claim.getIncidentDate(),
                claim.getOfficerRemarks(),
                claim.getGarage() != null ? claim.getGarage().getGarageName() : null,
                claim.getGarage() != null ? claim.getGarage().getAddress() : null,
                claim.getRepairBillAmount(),
                claim.getReceiptPath(),
                claim.getCreatedAt(),
                claim.getCustomer().getName(),
                claim.getOfficer() != null ? claim.getOfficer().getName() : null
        );
    }
}
