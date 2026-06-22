package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.CustomerProfileDto;
import com.InsuranceSystem.model.Customer;

public class CustomerMapper {

    public static CustomerProfileDto toProfileDto(Customer customer) {
        return new CustomerProfileDto(
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getUser().getRole().toString()
        );
    }
}
