package com.InsuranceSystem.controller;

import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/api/customer/getById/{customerId}")
    public ResponseEntity<Customer> getById(@PathVariable int customerId){
        return ResponseEntity
                .ok(customerService.getById(customerId));

    }
}
