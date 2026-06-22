package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.CustomerStatsDto;
import com.InsuranceSystem.repository.ClaimRepository;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.PolicyRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerStatsService {

    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;
    private final ProposalRepository proposalRepository;
    private final ClaimRepository claimRepository;

    public CustomerStatsDto getCustomerStats(String username) {

        // Step 1: Find customer
        int customerId = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"))
                .getCustomerId();

        // Step 2: Get counts
        long totalQuotes = proposalRepository.countByCustomerId(customerId);
        long myProposals = proposalRepository.countSubmittedByCustomerId(customerId);
        long activePolicies = policyRepository.countActiveByCustomerId(customerId);
        long claimsFiled = claimRepository.countByCustomerCustomerId(customerId);

        // Step 3: Prepare labels and values
        List<String> labels = List.of(
                "Total Quotes",
                "My Proposals",
                "Active Policies",
                "Claims Filed"
        );

        List<Long> values = List.of(
                totalQuotes,
                myProposals,
                activePolicies,
                claimsFiled
        );

        return new CustomerStatsDto(
                "Customer Dashboard Stats",
                labels,
                values
        );
    }
}
