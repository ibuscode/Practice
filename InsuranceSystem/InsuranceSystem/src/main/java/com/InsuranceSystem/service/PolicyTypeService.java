package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.AddOnDto;
import com.InsuranceSystem.dto.PolicyTypeDto;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.PolicyTypeMapper;
import com.InsuranceSystem.model.AddOn;
import com.InsuranceSystem.model.PolicyType;
import com.InsuranceSystem.repository.AddOnRepository;
import com.InsuranceSystem.repository.PolicyTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PolicyTypeService {

    private final PolicyTypeRepository policyTypeRepository;
    private final AddOnRepository addOnRepository;

    public List<PolicyType> getAll() {
        return policyTypeRepository.findAll();
    }

    public PolicyType getPolicyType(int id) {
        return policyTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Policy Type Id"));
    }

    public PolicyTypeDto getById(int id) {
        PolicyType policyType = getPolicyType(id);
        List<AddOn> addOns = addOnRepository.findByPolicyTypeId(id);
        return PolicyTypeMapper.policyTypeToDto(policyType, addOns);
    }

    public PolicyTypeDto create(PolicyTypeDto dto) {
        PolicyType policyType = PolicyTypeMapper.dtoToPolicyType(dto);
        policyTypeRepository.save(policyType);

        List<AddOn> addOns = addOnRepository.findByPolicyTypeId(policyType.getId());
        return PolicyTypeMapper.policyTypeToDto(policyType, addOns);
    }

    public PolicyTypeDto update(int id, PolicyTypeDto dto) {
        PolicyType policyType = getPolicyType(id);

        policyType.setPolicyName(dto.policyName());
        policyType.setDescription(dto.description());
        policyType.setBasePremium(dto.basePremium());
        policyType.setCoverageType(dto.coverageType());
        policyType.setActive(dto.active());
        policyTypeRepository.save(policyType);

        List<AddOn> addOns = addOnRepository.findByPolicyTypeId(id);
        return PolicyTypeMapper.policyTypeToDto(policyType, addOns);
    }

    public List<AddOnDto> getAddOns(int policyTypeId) {
        getPolicyType(policyTypeId);
        return addOnRepository.findByPolicyTypeId(policyTypeId)
                .stream()
                .map(PolicyTypeMapper::addOnToDto)
                .toList();
    }

    public AddOnDto createAddOn(int policyTypeId, AddOnDto dto) {
        PolicyType policyType = getPolicyType(policyTypeId);

        AddOn addOn = PolicyTypeMapper.dtoToAddOn(dto);
        addOn.setPolicyType(policyType);
        addOnRepository.save(addOn);

        return PolicyTypeMapper.addOnToDto(addOn);
    }

    public AddOnDto updateAddOn(int id, AddOnDto dto) {
        AddOn addOn = addOnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Add On Id"));

        addOn.setAddonName(dto.addonName());
        addOn.setDescription(dto.description());
        addOn.setPrice(dto.price());
        addOnRepository.save(addOn);

        return PolicyTypeMapper.addOnToDto(addOn);
    }

    public void deleteAddOn(int id) {
        AddOn addOn = addOnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Add On Id"));

        addOnRepository.delete(addOn);
    }
}
