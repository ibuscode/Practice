package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.PaymentRequestDto;
import com.InsuranceSystem.dto.PaymentResponseDto;
import com.InsuranceSystem.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentResponseDto makePayment(@Valid @RequestBody PaymentRequestDto request, Principal principal) {
        String username = principal.getName();
        return paymentService.makePayment(request, username);
    }

}
