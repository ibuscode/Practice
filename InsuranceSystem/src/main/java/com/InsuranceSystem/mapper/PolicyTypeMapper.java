package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.AddOnDto;
import com.InsuranceSystem.dto.PolicyTypeDto;
import com.InsuranceSystem.model.AddOn;
import com.InsuranceSystem.model.PolicyType;

import java.util.List;

public class PolicyTypeMapper {

    public static PolicyType dtoToPolicyType(PolicyTypeDto dto) {
        PolicyType policyType = new PolicyType();
        policyType.setPolicyName(dto.policyName());
        policyType.setDescription(dto.description());
        policyType.setBasePremium(dto.basePremium());
        policyType.setCoverageType(dto.coverageType());
        policyType.setActive(dto.active());
        return policyType;
    }

    public static PolicyTypeDto policyTypeToDto(PolicyType policyType, List<AddOn> addOns) {
        List<AddOnDto> addOnDtos = addOns.stream()
                .map(PolicyTypeMapper::addOnToDto)
                .toList();

        return new PolicyTypeDto(
                policyType.getId(),
                policyType.getPolicyName(),
                policyType.getDescription(),
                policyType.getBasePremium(),
                policyType.getCoverageType(),
                policyType.isActive(),
                addOnDtos
        );
    }

    public static AddOn dtoToAddOn(AddOnDto dto) {
        AddOn addOn = new AddOn();
        addOn.setAddonName(dto.addonName());
        addOn.setDescription(dto.description());
        addOn.setPrice(dto.price());
        return addOn;
    }

    public static AddOnDto addOnToDto(AddOn addOn) {
        return new AddOnDto(
                addOn.getAddonId(),
                addOn.getAddonName(),
                addOn.getDescription(),
                addOn.getPrice()
        );
    }
}
