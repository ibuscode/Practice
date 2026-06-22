package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.OfficerStatsDto;
import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.repository.ClaimRepository;
import com.InsuranceSystem.repository.OfficerRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfficerStatsService {

    private final OfficerRepository officerRepository;
    private final ProposalRepository proposalRepository;
    private final ClaimRepository claimRepository;

    public OfficerStatsDto getOfficerStats(String username) {

        // Step 1: Find officer
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        int officerId = officer.getOfficerId();

        // Step 2: Get Proposal Stats
        long pendingProposals = proposalRepository.countByOfficerIdAndStatus(
                officerId, ProposalStatus.ASSIGNED);
        long approvedProposals = proposalRepository.countByOfficerIdAndStatus(
                officerId, ProposalStatus.APPROVED);
        long rejectedProposals = proposalRepository.countByOfficerIdAndStatus(
                officerId, ProposalStatus.REJECTED);

        // Step 3: Get Claim Stats
        long pendingClaims = claimRepository.countByOfficerIdAndStatuses(
                officerId, List.of(ClaimStatus.SUBMITTED, ClaimStatus.UNDER_REVIEW));
        long approvedClaims = claimRepository.countByOfficerIdAndStatus(
                officerId, ClaimStatus.APPROVED);
        long rejectedClaims = claimRepository.countByOfficerIdAndStatus(
                officerId, ClaimStatus.REJECTED);

        // Step 4: Prepare response
        List<String> proposalLabels = List.of("Pending", "Approved", "Rejected");
        List<Long> proposalValues = List.of(pendingProposals, approvedProposals, rejectedProposals);

        List<String> claimLabels = List.of("Pending", "Approved", "Rejected");
        List<Long> claimValues = List.of(pendingClaims, approvedClaims, rejectedClaims);

        return new OfficerStatsDto(
                "Officer Dashboard Stats",
                proposalLabels,
                proposalValues,
                claimLabels,
                claimValues
        );
    }
}
