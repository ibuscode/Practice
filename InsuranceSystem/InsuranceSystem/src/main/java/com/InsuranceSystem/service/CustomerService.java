package com.InsuranceSystem.service;

import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    public Customer getById(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Customer Id"));


    }
}
