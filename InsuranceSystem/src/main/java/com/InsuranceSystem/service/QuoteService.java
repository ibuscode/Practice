package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.QuoteRequestDto;
import com.InsuranceSystem.dto.QuoteResponseDto;
import com.InsuranceSystem.mapper.QuoteMapper;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Quote;
import com.InsuranceSystem.model.Vehicle;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.QuoteRepository;
import com.InsuranceSystem.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final QuoteRepository quoteRepository;

    private final PremiumCalculationService premiumService;

    public QuoteResponseDto generateQuote(@Valid QuoteRequestDto request, String username) {

        // Step 1: Find customer
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Step 2: Find existing vehicle or create new using Mapper
        Vehicle vehicle = vehicleRepository.findByRegNo(request.regNo())
                .orElseGet(() -> {
                    Vehicle newVehicle = QuoteMapper.toVehicleEntity(request, customer);
                    return vehicleRepository.save(newVehicle);
                });

        // Step 3: Calculate all values using PremiumCalculationService
        int vehicleAge = premiumService.calculateVehicleAge(request.manufacturingYear());
        double ageCharge = premiumService.calculateAgeCharge(request.manufacturingYear());
        double baseAmount = premiumService.calculateBaseAmount(request.vehicleType());
        double policyCharge = premiumService.calculatePolicyCharge(request.policyType());
        double coverageCharge = premiumService.calculateCoverageCharge(request.coverageAmount());
        double addOnCharge = premiumService.calculateAddOnCharge(request.addOns(), request.policyType());
        double totalPremium = baseAmount + ageCharge + policyCharge + coverageCharge + addOnCharge;

        // Step 4: Create Quote entity using Mapper
        Quote quote = QuoteMapper.toQuoteEntity(request, vehicle, customer, baseAmount, totalPremium);

        // Step 5: Save quote
        Quote savedQuote = quoteRepository.save(quote);

        // Step 6: Return Response DTO using Mapper
        return QuoteMapper.toResponseDto(savedQuote, vehicleAge, ageCharge, policyCharge, coverageCharge, addOnCharge);
    }
    }

