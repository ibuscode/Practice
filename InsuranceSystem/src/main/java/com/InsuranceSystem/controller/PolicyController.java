package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.PolicyResponseDto;
import com.InsuranceSystem.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class PolicyController {

    private final PaymentService paymentService;

    @GetMapping("/my-policies")
    public List<PolicyResponseDto> getMyPolicies(Principal principal) {
        String username = principal.getName();
        return paymentService.getMyPolicies(username);
    }
}
