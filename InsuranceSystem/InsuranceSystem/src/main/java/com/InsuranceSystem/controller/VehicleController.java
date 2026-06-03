package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.VehicleResDto;
import com.InsuranceSystem.service.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/api/vehicle/add/{customerId}")
    public void addVehicle(@PathVariable int customerId, @Valid @RequestBody VehicleResDto dto) {
        vehicleService.add(customerId, dto);
    }

    @GetMapping("/api/vehicle/customer/getAll/{customerId}")
    public List<VehicleResDto> getAllVehicle(@PathVariable int customerId) {

        return vehicleService.getById(customerId);


    }
}
