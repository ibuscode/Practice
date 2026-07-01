package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.CustomerProfileDto;
import com.InsuranceSystem.dto.CustomerProposalDto;
import com.InsuranceSystem.dto.CustomerUpdateDto;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.CustomerMapper;
import com.InsuranceSystem.mapper.ProposalMapper;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.PaymentRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProposalRepository proposalRepository;
    private final PaymentRepository paymentRepository;
    public Customer getById(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Customer Id"));


    }
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<CustomerProposalDto> getMyProposals(String username) {

        Customer customer = getCustomerByUsername(username);

        List<Proposal> proposals = proposalRepository.findByCustomerId(customer.getCustomerId());

        return proposals.stream()
                .map(proposal -> {
                    boolean isPaid = paymentRepository.existsByProposalProposalId(proposal.getProposalId());
                    return ProposalMapper.toCustomerDto(proposal, isPaid);
                })                .toList();
    }

    public CustomerProfileDto getCustomerProfile(String username) {
        Customer customer = customerRepository.findCustomerWithUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return CustomerMapper.toProfileDto(customer);
    }

    public CustomerProfileDto editCustomerProfile(String username, CustomerUpdateDto request) {
        Customer customer = customerRepository.findCustomerWithUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAddress(request.address());
        customer.setPhoneNumber(request.phoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        return CustomerMapper.toProfileDto(updatedCustomer);
    }

}
