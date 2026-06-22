package com.InsuranceSystem.service;

import com.InsuranceSystem.enums.AddOn;
import com.InsuranceSystem.enums.PolicyType;
import com.InsuranceSystem.enums.VehicleType;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class PremiumCalculationService {
    private static final int CURRENT_YEAR = Year.now().getValue();

    public int calculateVehicleAge(int manufacturingYear) {
        return CURRENT_YEAR - manufacturingYear;
    }

    public double calculateBaseAmount(VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE: return 2000;
            case CAMPERVAN: return 5000;
            case TRUCK: return 8000;
            default: return 4000;
        }
    }

    public double calculateAgeCharge(int manufacturingYear) {
        int age = calculateVehicleAge(manufacturingYear);
        if(age <= 2) return 500;
        else if(age <= 5) return 1000;
        else return 2000;
    }

    public double calculatePolicyCharge(PolicyType policyType) {
        switch (policyType) {
            case THIRD_PARTY: return 1000;
            case OWN_DAMAGE: return 2000;
            case COMPREHENSIVE: return 3000;
            default: return 1500;
        }
    }

    public double calculateCoverageCharge(double coverageAmount) {
        if(coverageAmount <= 200000) return 500;
        else if(coverageAmount <= 500000) return 1000;
        else return 2000;
    }

    public double calculateAddOnCharge(List<AddOn> addOns, PolicyType policyType) {
        if (policyType == PolicyType.THIRD_PARTY || addOns == null || addOns.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (AddOn addOn : addOns) {
            switch (addOn) {
                case ZERO_DEPRECIATION:   total += 1500; break;
                case ENGINE_PROTECTION:   total += 1200; break;
                case RETURN_TO_INVOICE:   total += 1000; break;
                case NCB_PROTECTION:      total += 800;  break;
                case ROADSIDE_ASSISTANCE: total += 500;  break;
                case CONSUMABLES_COVER:   total += 600;  break;
                default:                  total += 400;  break;
            }
        }
        return total;
    }
}
