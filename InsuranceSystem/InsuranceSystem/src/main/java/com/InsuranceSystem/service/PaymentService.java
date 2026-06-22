package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.PaymentRequestDto;
import com.InsuranceSystem.dto.PaymentResponseDto;
import com.InsuranceSystem.dto.PolicyResponseDto;
import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.mapper.PaymentMapper;
import com.InsuranceSystem.mapper.PolicyMapper;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Payment;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.PaymentRepository;
import com.InsuranceSystem.repository.PolicyRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProposalRepository proposalRepository;
    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;

    public PaymentResponseDto makePayment(@Valid PaymentRequestDto request, String username) {
        // Step 1: Find customer
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Step 2: Find proposal
        Proposal proposal = proposalRepository.findById(request.proposalId())
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // Step 3: Check if proposal belongs to this customer
        if(proposal.getCustomer().getCustomerId()!= customer.getCustomerId()) {
            throw new RuntimeException("You can only pay for your own proposals");
        }

        // Step 4: Check if proposal status is APPROVED
        if(proposal.getProposalStatus() != ProposalStatus.APPROVED) {
            throw new RuntimeException("Payment is only allowed for APPROVED proposals. Current status: " + proposal.getProposalStatus());
        }
        if(paymentRepository.existsByProposalProposalId(proposal.getProposalId())) {
            throw new RuntimeException("Payment already made for this proposal");
        }

        // Step 5: Create payment using Mapper
        Payment payment = PaymentMapper.toEntity(request, customer, proposal);

        // Step 6: Save payment
        Payment savedPayment = paymentRepository.save(payment);

        Policy policy = PolicyMapper.toEntity(proposal);
        policyRepository.save(policy);

        // Step 7: Return response using Mapper
        return PaymentMapper.toDto(savedPayment);
    }

    public List<PolicyResponseDto> getMyPolicies(String username) {

        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Policy> policies = policyRepository.findByProposalCustomerCustomerId(customer.getCustomerId());

        return policies.stream()
                .map(PolicyMapper::toDto)
                .toList();
    }
}
