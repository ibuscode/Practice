package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.GarageRequestDto;
import com.InsuranceSystem.dto.GarageResponseDto;
import com.InsuranceSystem.dto.GarageStatusUpdateDto;
import com.InsuranceSystem.service.GarageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garages")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class GarageController {

    private final GarageService garageService;

    // Admin: Add new garage
    @PostMapping("/add")
    public GarageResponseDto addGarage(@Valid @RequestBody GarageRequestDto request) {
        return garageService.addGarage(request);
    }

    // Admin: Get all garages with pagination
    @GetMapping("/all")
    public Page<GarageResponseDto> getAllGarages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return garageService.getAllGarages(page, size);
    }

    // Admin: Update garage status (Activate/Deactivate)
    @PutMapping("/{garageId}/status")
    public GarageResponseDto updateGarageStatus(
            @PathVariable int garageId,
            @Valid @RequestBody GarageStatusUpdateDto request) {
        return garageService.updateGarageStatus(garageId, request);
    }

    // Customer: Get all ACTIVE garages
    @GetMapping("/active")
    public List<GarageResponseDto> getActiveGarages() {
        return garageService.getActiveGarages();
    }
}
