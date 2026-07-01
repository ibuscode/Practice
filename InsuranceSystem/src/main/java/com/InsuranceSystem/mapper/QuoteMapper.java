package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.QuoteRequestDto;
import com.InsuranceSystem.dto.QuoteResponseDto;
import com.InsuranceSystem.enums.QuoteStatus;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Quote;
import com.InsuranceSystem.model.Vehicle;

import java.time.LocalDate;

public class QuoteMapper {

    public static Vehicle toVehicleEntity(QuoteRequestDto request, Customer customer) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNo(request.regNo());
        vehicle.setVehicleType(request.vehicleType());
        vehicle.setManufacturer(request.manufacturer());
        vehicle.setModel(request.model());
        vehicle.setYear(request.manufacturingYear());
        vehicle.setCustomer(customer);
        return vehicle;
    }

    // Convert Request DTO + calculated values to Quote Entity
    public static Quote toQuoteEntity(QuoteRequestDto request, Vehicle vehicle, Customer customer,
                                      double baseAmount, double premiumAmount) {
        Quote quote = new Quote();
        quote.setCoverageAmount(request.coverageAmount());
        quote.setPremiumAmount(premiumAmount);
        quote.setBaseAmount(baseAmount);
        quote.setValidUntil(LocalDate.now().plusDays(15));
        quote.setQuoteStatus(QuoteStatus.ACTIVE);
        quote.setCustomer(customer);
        quote.setVehicle(vehicle);
        return quote;
    }

    // Convert Quote Entity to Response DTO
    public static QuoteResponseDto toResponseDto(Quote quote, int vehicleAge, double ageCharge,
                                                 double policyCharge, double coverageCharge, double addOnCharge) {
        return new QuoteResponseDto(
                quote.getQuoteId(),
                quote.getCoverageAmount(),
                quote.getPremiumAmount(),
                quote.getBaseAmount(),
                quote.getValidUntil(),
                quote.getQuoteStatus().toString(),
                vehicleAge,
                ageCharge,
                policyCharge,
                coverageCharge,
                addOnCharge
        );
    }
}
