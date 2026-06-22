package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.PolicyResponseDto;
import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Proposal;

import java.time.LocalDate;
import java.util.UUID;

public class PolicyMapper {

    private static int counter = 1;

    public static Policy toEntity(Proposal proposal) {
        Policy policy = new Policy();
        policy.setPolicyNumber(generateUniquePolicyNumber());
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));
        policy.setPolicyStatus(PolicyStatus.ACTIVE);
        policy.setProposal(proposal);
        return policy;
    }

    public static PolicyResponseDto toDto(Policy policy) {
        return new PolicyResponseDto(
                policy.getPolicyId(),
                policy.getPolicyNumber(),
                policy.getStartDate(),
                policy.getEndDate(),
                policy.getPolicyStatus(),
                policy.getIssuedAt(),
                policy.getProposal().getProposalId(),
                policy.getProposal().getCustomer().getName(),
                policy.getProposal().getVehicle().getRegNo(),
                policy.getProposal().getQuote().getCoverageAmount(),
                policy.getProposal().getQuote().getPremiumAmount()
        );
    }


    private static String generateUniquePolicyNumber() {
        // ✅ Include timestamp for uniqueness
        return "POL-" + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
