package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.AddOnDto;
import com.InsuranceSystem.dto.ClaimSummaryDto;
import com.InsuranceSystem.dto.PolicyResponseDto;
import com.InsuranceSystem.dto.PolicyTypeDto;
import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.model.Claim;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Quote;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PolicyMapper {

    public static PolicyResponseDto policyToDto(Policy policy, Quote quote, List<Claim> claims) {
        long daysLeft = 0;
        if (policy.getPolicyStatus() == PolicyStatus.ACTIVE) {
            daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), policy.getEndDate());
        }

        double premiumPaid = 0;
        if (quote != null) {
            premiumPaid = quote.getPremiumAmount();
        }

        List<AddOnDto> addOns = policy.getProposal().getSelectedAddOns()
                .stream()
                .map(PolicyTypeMapper::addOnToDto)
                .toList();

        List<ClaimSummaryDto> claimHistory = claims.stream()
                .map(ClaimMapper::claimToSummaryDto)
                .toList();

        PolicyTypeDto policyTypeDto = PolicyTypeMapper.policyTypeToDto(
                policy.getProposal().getPolicyType(),
                policy.getProposal().getSelectedAddOns()
        );

        return new PolicyResponseDto(
                policy.getPolicyId(),
                policy.getPolicyNumber(),
                policy.getStartDate(),
                policy.getEndDate(),
                policy.getPolicyStatus(),
                daysLeft,
                premiumPaid,
                policy.getIssuedAt(),
                UserMapper.customerToProfileDto(policy.getProposal().getCustomer()),
                VehicleMapper.entityToDto(policy.getProposal().getVehicle()),
                policyTypeDto,
                addOns,
                claimHistory
        );
    }
}
