package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.PolicyResponseDto;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.service.PolicyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/api/policy/all")
    public List<Policy> getAll()
    {
      return policyService.getAll();

    }

    @GetMapping("/api/policies/user/{userId}")
    public List<PolicyResponseDto> getByUser(@PathVariable int userId, Principal principal) {
        return policyService.getByUser(userId, principal.getName());
    }

    @GetMapping("/api/policies/getBy-id/{id}")
    public PolicyResponseDto getById(@PathVariable int id, Principal principal) {
        return policyService.getById(id, principal.getName());
    }

}
