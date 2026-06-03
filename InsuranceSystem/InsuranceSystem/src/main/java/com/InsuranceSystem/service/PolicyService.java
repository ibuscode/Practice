package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.PolicyResponseDto;
import com.InsuranceSystem.enums.Role;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.PolicyMapper;
import com.InsuranceSystem.model.Claim;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Quote;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.repository.ClaimRepository;
import com.InsuranceSystem.repository.PolicyRepository;
import com.InsuranceSystem.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final QuoteRepository quoteRepository;
    private final ClaimRepository claimRepository;
    private final UserService userService;

    public List<Policy> getAll() {
        return policyRepository.findAll();
    }

    public List<PolicyResponseDto> getByUser(int userId, String username) {
        User loginUser = (User) userService.loadUserByUsername(username);

        if (loginUser.getRole() == Role.CUSTOMER && loginUser.getId() != userId) {
            throw new RuntimeException("You can see only your policies");
        }

        return policyRepository.findByProposalCustomerUserId(userId)
                .stream()
                .map(this::policyToDto)
                .toList();
    }

    public PolicyResponseDto getById(int id, String username) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Policy Id"));

        User loginUser = (User) userService.loadUserByUsername(username);
        int policyUserId = policy.getProposal().getCustomer().getUser().getId();

        if (loginUser.getRole() == Role.CUSTOMER && loginUser.getId() != policyUserId) {
            throw new RuntimeException("You can see only your policy");
        }

        return policyToDto(policy);
    }

    private PolicyResponseDto policyToDto(Policy policy) {
        Quote quote = quoteRepository.findTopByProposalProposalIdOrderByGeneratedAtDesc(
                policy.getProposal().getProposalId()
        ).orElse(null);

        List<Claim> claims = claimRepository.findByPolicyPolicyId(policy.getPolicyId());

        return PolicyMapper.policyToDto(policy, quote, claims);
    }
}
