package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.CustomerRegisterDto;
import com.InsuranceSystem.dto.OfficerDto;
import com.InsuranceSystem.dto.UserProfileDto;
import com.InsuranceSystem.enums.Role;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.model.User;

public class UserMapper {

    public static User customerDtoToUser(CustomerRegisterDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(Role.CUSTOMER);
        return user;
    }

    public static Customer customerDtoToCustomer(CustomerRegisterDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setAddress(dto.address());
        customer.setDateOfBirth(dto.dateOfBirth());
        customer.setAadhaarNumber(dto.aadhaarNumber());
        customer.setPanNumber(dto.panNumber());
        customer.setPhoneNumber(dto.phoneNumber());
        return customer;
    }

    public static User officerDtoToUser(OfficerDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(Role.OFFICER);
        return user;
    }

    public static Officer officerDtoToOfficer(OfficerDto dto) {
        Officer officer = new Officer();
        officer.setName(dto.name());
        officer.setEmail(dto.email());
        return officer;
    }

    public static UserProfileDto customerToProfileDto(Customer customer) {
        User user = customer.getUser();
        return new UserProfileDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getDateOfBirth(),
                customer.getAadhaarNumber(),
                customer.getPanNumber(),
                customer.getPhoneNumber()
        );
    }

    public static UserProfileDto officerToProfileDto(Officer officer) {
        User user = officer.getUser();
        return new UserProfileDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                officer.getName(),
                officer.getEmail(),
                null,
                null,
                null,
                null,
                null
        );
    }
}
