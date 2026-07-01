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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;

    public Vehicle addVehicle(@Valid VehicleRequestDto dto, String username) {
        log.info("Adding vehicle for user: {}", username);

        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> {
                    log.error("Customer not found for username: {}", username);
                    return new RuntimeException("Customer not found");
                });

        Vehicle vehicle = VehicleMapper.toEntity(dto, customer);
        Vehicle saved = vehicleRepository.save(vehicle);

        log.info("Vehicle added successfully with ID: {} for user: {}", saved.getVehicleId(), username);
        return saved;
    }

    public List<VehicleResponseDto> getMyVehicles(String username) {
        log.info("Fetching vehicles for user: {}", username);

        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> {
                    log.error("Customer not found for username: {}", username);
                    return new RuntimeException("Customer not found");
                });

        List<Vehicle> vehicles = vehicleRepository.findByCustomerCustomerId(customer.getCustomerId());

        log.info("Found {} vehicle(s) for user: {}", vehicles.size(), username);

        return vehicles.stream()
                .map(VehicleMapper::toDto)
                .toList();
    }

    public List<VehicleWithPoliciesDto> getMyVehiclesWithPolicies(String username) {
        log.info("Fetching vehicles with policies for user: {}", username);

        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> {
                    log.error("Customer not found for username: {}", username);
                    return new RuntimeException("Customer not found");
                });

        List<Vehicle> vehicles = vehicleRepository.findByCustomerCustomerId(customer.getCustomerId());
        log.info("Found {} vehicle(s) for customer ID: {}", vehicles.size(), customer.getCustomerId());

        List<VehicleWithPoliciesDto> result = vehicles.stream()
                .map(vehicle -> {
                    List<Policy> policies = policyRepository.findByProposalVehicleVehicleId(vehicle.getVehicleId());
                    log.info("Vehicle ID: {} has {} policy(ies)", vehicle.getVehicleId(), policies.size());
                    return VehicleMapper.toVehicleWithPoliciesDto(vehicle, policies);
                })
                .collect(Collectors.toList());

        log.info("Successfully fetched vehicles with policies for user: {}", username);
        return result;
    }
}