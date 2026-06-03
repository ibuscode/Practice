package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.ClaimRequestDto;
import com.InsuranceSystem.dto.ClaimResponseDto;
import com.InsuranceSystem.service.ClaimService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping("/api/claims/create")
    public ClaimResponseDto create(@Valid @RequestBody ClaimRequestDto dto, Principal principal) {
        return claimService.create(dto, principal.getName());
    }

    @GetMapping("/api/claims/get-user/{userId}")
    public List<ClaimResponseDto> getByUser(@PathVariable int userId, Principal principal) {
        return claimService.getByUser(userId, principal.getName());
    }
}
