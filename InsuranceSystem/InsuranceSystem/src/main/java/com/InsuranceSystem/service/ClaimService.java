package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.ClaimRequestDto;
import com.InsuranceSystem.dto.ClaimResponseDto;
import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.enums.Role;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.ClaimMapper;
import com.InsuranceSystem.model.Claim;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.repository.ClaimRepository;
import com.InsuranceSystem.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final UserService userService;

    public ClaimResponseDto create(ClaimRequestDto dto, String username) {
        User loginUser = (User) userService.loadUserByUsername(username);

        Policy policy = policyRepository.findById(dto.policyId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Policy Id"));

        int policyUserId = policy.getProposal().getCustomer().getUser().getId();
        if (loginUser.getId() != policyUserId) {
            throw new RuntimeException("You can file claim only for your policy");
        }

        if (policy.getPolicyStatus() != PolicyStatus.ACTIVE) {
            throw new RuntimeException("Claim allowed only for active policy");
        }

        LocalDate incidentDate = dto.incidentDate();
        if (incidentDate == null) {
            incidentDate = LocalDate.now();
        }

        if (incidentDate.isBefore(policy.getStartDate()) || incidentDate.isAfter(policy.getEndDate())) {
            throw new RuntimeException("Claim must be within policy period");
        }

        Claim claim = new Claim();
        claim.setPolicy(policy);
        claim.setCustomer(policy.getProposal().getCustomer());
        claim.setIncidentDate(incidentDate);
        claim.setDescription(dto.description());
        claim.setClaimedAmount(dto.claimedAmount());
        claim.setClaimStatus(ClaimStatus.SUBMITTED);
        claimRepository.save(claim);

        return ClaimMapper.claimToDto(claim);
    }

    public List<ClaimResponseDto> getByUser(int userId, String username) {
        User loginUser = (User) userService.loadUserByUsername(username);

        if (loginUser.getRole() == Role.CUSTOMER && loginUser.getId() != userId) {
            throw new RuntimeException("You can see only your claims");
        }

        return claimRepository.findByCustomerUserIdOrderByClaimDateDesc(userId)
                .stream()
                .map(ClaimMapper::claimToDto)
                .toList();
    }
}
