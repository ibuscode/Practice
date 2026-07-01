package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.CustomerProfileDto;
import com.InsuranceSystem.dto.CustomerProposalDto;
import com.InsuranceSystem.dto.CustomerStatsDto;
import com.InsuranceSystem.dto.CustomerUpdateDto;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.service.CustomerService;
import com.InsuranceSystem.service.CustomerStatsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class CustomerController {

    private final CustomerService customerService;
    private final CustomerStatsService customerStatsService;


    @GetMapping("/api/customer/getById/{customerId}")
    public ResponseEntity<Customer> getById(@PathVariable int customerId){
        return ResponseEntity
                .ok(customerService.getById(customerId));

    }
    @GetMapping("/api/customer/my-proposals")
    public List<CustomerProposalDto> getMyProposals(Principal principal) {
        String username = principal.getName();
        return customerService.getMyProposals(username);
    }

    @GetMapping("/api/customer/profile")
    public CustomerProfileDto getCustomerProfile(Principal principal) {
        String username = principal.getName();
        return customerService.getCustomerProfile(username);
    }

    @PutMapping("/api/customer/profile/edit")
    public CustomerProfileDto editCustomerProfile(@Valid @RequestBody CustomerUpdateDto request,
                                                  Principal principal) {
        String username = principal.getName();
        return customerService.editCustomerProfile(username, request);
    }

    @GetMapping("/api/customer/stat/by-type")
    public CustomerStatsDto getCustomerStats(Principal principal) {
        String username = principal.getName();
        return customerStatsService.getCustomerStats(username);
    }
}
