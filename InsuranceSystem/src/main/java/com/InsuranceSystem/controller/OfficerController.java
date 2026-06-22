package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.OfficerProfileDto;
import com.InsuranceSystem.dto.OfficerResponseDto;
import com.InsuranceSystem.dto.OfficerStatsDto;
import com.InsuranceSystem.service.OfficerService;
import com.InsuranceSystem.service.OfficerStatsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class OfficerController {

    private final OfficerService officerService;
    private final OfficerStatsService officerStatsService;


    // GET all officers
    @GetMapping("/officers")
    public List<OfficerResponseDto> getAllOfficers() {
        return officerService.getAllOfficers();
    }

    // GET officer by ID (optional)
    @GetMapping("/officers/{officerId}")
    public OfficerResponseDto getOfficerById(@PathVariable int officerId) {
        return officerService.getOfficerById(officerId);
    }

    @GetMapping("/officer/profile")
    public OfficerProfileDto getOfficerProfile(Principal principal) {
        String username = principal.getName();
        return officerService.getOfficerProfile(username);
    }

    @GetMapping("/officer/stats")
    public OfficerStatsDto getOfficerStats(Principal principal) {
        String username = principal.getName();
        return officerStatsService.getOfficerStats(username);
    }
}