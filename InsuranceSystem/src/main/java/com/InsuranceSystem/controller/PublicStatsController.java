package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.PublicStatsDto;
import com.InsuranceSystem.service.PublicStatsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class PublicStatsController {

    private final PublicStatsService publicStatsService;

    @GetMapping("/stats")
    public PublicStatsDto getPublicStats() {
        return publicStatsService.getPublicStats();
    }
}
