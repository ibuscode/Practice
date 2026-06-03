package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.AddOnDto;
import com.InsuranceSystem.dto.PolicyTypeDto;
import com.InsuranceSystem.model.PolicyType;
import com.InsuranceSystem.service.PolicyTypeService;
import com.InsuranceSystem.utility.ResponseUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PolicyTypeController {

    private final PolicyTypeService policyTypeService;
    @GetMapping("/api/policyType/getAll")
    public List<PolicyType> getAll() {
       return policyTypeService.getAll();
    }

    @GetMapping("/api/v1/policy-types/{id}")
    public PolicyTypeDto getById(@PathVariable int id) {
        return policyTypeService.getById(id);
    }

    @PostMapping("/api/v1/policy-types")
    public PolicyTypeDto create(@Valid @RequestBody PolicyTypeDto dto) {
        return policyTypeService.create(dto);
    }

    @PutMapping("/api/v1/policy-types/{id}")
    public PolicyTypeDto update(@PathVariable int id, @Valid @RequestBody PolicyTypeDto dto) {
        return policyTypeService.update(id, dto);
    }

    @GetMapping("/api/v1/policy-types/{id}/addons")
    public List<AddOnDto> getAddOns(@PathVariable int id) {
        return policyTypeService.getAddOns(id);
    }

    @PostMapping("/api/v1/policy-types/{id}/addons")
    public AddOnDto createAddOn(@PathVariable int id, @Valid @RequestBody AddOnDto dto) {
        return policyTypeService.createAddOn(id, dto);
    }

    @PutMapping("/api/v1/addons/{id}")
    public AddOnDto updateAddOn(@PathVariable int id, @Valid @RequestBody AddOnDto dto) {
        return policyTypeService.updateAddOn(id, dto);
    }

    @DeleteMapping("/api/v1/addons/{id}")
    public ResponseUtility deleteAddOn(@PathVariable int id) {
        policyTypeService.deleteAddOn(id);
        return new ResponseUtility("Add On deleted successfully");
    }
}
