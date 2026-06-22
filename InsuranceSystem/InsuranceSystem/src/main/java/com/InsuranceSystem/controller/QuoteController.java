package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.QuoteRequestDto;
import com.InsuranceSystem.dto.QuoteResponseDto;
import com.InsuranceSystem.service.QuoteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/quotes")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping("/generate")
    public QuoteResponseDto generateQuote(@Valid @RequestBody QuoteRequestDto request, Principal principal) {
        String username = principal.getName();
        return quoteService.generateQuote(request, username);
    }

}
