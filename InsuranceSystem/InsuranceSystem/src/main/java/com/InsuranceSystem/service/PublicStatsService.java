package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.PublicStatsDto;
import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.repository.ClaimRepository;
import com.InsuranceSystem.repository.GarageRepository;
import com.InsuranceSystem.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PublicStatsService {

    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final GarageRepository garageRepository;

    public PublicStatsDto getPublicStats() {

        long activePolicies = policyRepository.countByPolicyStatus(PolicyStatus.ACTIVE);
        long claimsServed = claimRepository.countByClaimStatus(ClaimStatus.APPROVED);
        long garagesInNetwork = garageRepository.countByStatus(GarageStatus.ACTIVE);

        List<String> labels = List.of(
                "Active Policies",
                "Claims Served",
                "Garages in Network"
        );

        List<Long> values = List.of(
                activePolicies,
                claimsServed,
                garagesInNetwork
        );

        return new PublicStatsDto(
                "Insurance Platform Stats",
                labels,
                values
        );
    }
}
