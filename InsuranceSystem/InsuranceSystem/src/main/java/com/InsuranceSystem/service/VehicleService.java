package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.VehicleRequestDto;
import com.InsuranceSystem.dto.VehicleResponseDto;
import com.InsuranceSystem.dto.VehicleWithPoliciesDto;
import com.InsuranceSystem.mapper.VehicleMapper;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Vehicle;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.PolicyRepository;
import com.InsuranceSystem.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;



    public Vehicle addVehicle(@Valid VehicleRequestDto dto, String username) {
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));



        Vehicle vehicle = VehicleMapper.toEntity(dto, customer);

       return vehicleRepository.save(vehicle);
    }

    public List<VehicleResponseDto> getMyVehicles(String username) {
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Vehicle> vehicles = vehicleRepository.findByCustomerCustomerId(customer.getCustomerId());

        return vehicles.stream()
                .map(VehicleMapper::toDto)
                .toList();
    }

    public List<VehicleWithPoliciesDto> getMyVehiclesWithPolicies(String username) {

        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Vehicle> vehicles = vehicleRepository.findByCustomerCustomerId(customer.getCustomerId());

        return vehicles.stream()
                .map(vehicle -> {
                    List<Policy> policies = policyRepository.findByProposalVehicleVehicleId(vehicle.getVehicleId());
                    return VehicleMapper.toVehicleWithPoliciesDto(vehicle, policies);
                })
                .collect(Collectors.toList());
    }
    }

