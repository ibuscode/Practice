package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.VehicleRequestDto;
import com.InsuranceSystem.dto.VehicleResponseDto;
import com.InsuranceSystem.dto.VehicleWithPoliciesDto;
import com.InsuranceSystem.service.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class VehicleController {

    private final VehicleService vehicleService;
    @PostMapping("/add")
    public void addVehicle(@Valid @RequestBody VehicleRequestDto dto, Principal principal) {
        String username = principal.getName();
        vehicleService.addVehicle(dto, username);
    }

    @GetMapping("/my-vehicles")
    public List<VehicleResponseDto> getMyVehicles(Principal principal) {
        String username = principal.getName();
        return vehicleService.getMyVehicles(username);
    }

    @GetMapping("/my-vehicles/policies")
    public List<VehicleWithPoliciesDto> getMyVehiclesWithPolicies(Principal principal) {
        String username = principal.getName();
        return vehicleService.getMyVehiclesWithPolicies(username);
    }

}
