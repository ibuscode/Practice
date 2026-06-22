package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.VehiclePolicyDto;
import com.InsuranceSystem.dto.VehicleRequestDto;
import com.InsuranceSystem.dto.VehicleResponseDto;
import com.InsuranceSystem.dto.VehicleWithPoliciesDto;
import com.InsuranceSystem.enums.ClaimType;
import com.InsuranceSystem.enums.PolicyType;
import com.InsuranceSystem.enums.VehicleType;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Vehicle;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static Vehicle toEntity(VehicleRequestDto dto, Customer customer) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(dto.vehicleType());
        vehicle.setManufacturer(dto.manufacturer());
        vehicle.setModel(dto.model());
        vehicle.setYear(dto.year());
        vehicle.setRegNo(dto.regNo());
        vehicle.setCustomer(customer);
        return vehicle;
    }

    public static VehicleResponseDto toDto(Vehicle vehicle) {
        return new VehicleResponseDto(
                vehicle.getVehicleId(),
                vehicle.getVehicleType(),
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getRegNo(),
                vehicle.getCustomer().getName()
        );
    }

    public static VehiclePolicyDto toPolicyDto(Policy policy) {

        // ✅ Get PolicyType from Quote
        PolicyType policyType = null;
        if (policy.getProposal() != null && policy.getProposal().getQuote() != null) {
            policyType = policy.getProposal().getQuote().getPolicyType();
        }

        // ✅ Get ClaimType from Quote
        ClaimType claimType = null;
        if (policy.getProposal() != null && policy.getProposal().getQuote() != null) {
            claimType = policy.getProposal().getQuote().getClaimType();
        }

        // ✅ Get Coverage Amount from Quote
        double coverageAmount = 0;
        if (policy.getProposal() != null && policy.getProposal().getQuote() != null) {
            coverageAmount = policy.getProposal().getQuote().getCoverageAmount();
        }

        return new VehiclePolicyDto(
                policy.getPolicyNumber(),
                policyType,
                claimType,
                coverageAmount,
                policy.getEndDate().format(DATE_FORMATTER),
                policy.getPolicyStatus()
        );
    }
    public static VehicleWithPoliciesDto toVehicleWithPoliciesDto(Vehicle vehicle, List<Policy> policies) {

        // ✅ Build vehicle name from manufacturer + model
        String vehicleName = vehicle.getManufacturer() + " " + vehicle.getModel();

        // ✅ Convert each Policy to VehiclePolicyDto
        List<VehiclePolicyDto> policyDtos = policies.stream()
                .map(VehicleMapper::toPolicyDto)
                .collect(Collectors.toList());

        // ✅ Get VehicleType enum
        VehicleType vehicleType = vehicle.getVehicleType();

        return new VehicleWithPoliciesDto(
                vehicle.getVehicleId(),
                vehicleName,
                vehicle.getRegNo(),
                vehicle.getYear(),
                vehicleType,
                policyDtos.size(),
                policyDtos
        );
    }

    }




