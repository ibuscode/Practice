package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.QuoteRequestDto;
import com.InsuranceSystem.dto.QuoteResponseDto;
import com.InsuranceSystem.service.QuoteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/api/v1/quotes")
    public QuoteResponseDto generateQuote(@Valid @RequestBody QuoteRequestDto dto, Principal principal) {
        return quoteService.generateQuote(dto, principal.getName());
    }

    @GetMapping("/api/v1/quotes/{proposalId}")
    public QuoteResponseDto getLatestQuote(@PathVariable int proposalId, Principal principal) {
        return quoteService.getLatestQuote(proposalId, principal.getName());
    }

    @PutMapping("/api/v1/quotes/{id}/expire")
    public QuoteResponseDto expireQuote(@PathVariable int id) {
        return quoteService.expireQuote(id);
    }
}
