package com.demo.controller;

import com.demo.dto.ApplicationResponseDto;
import com.demo.dto.ApplyForJobDto;
import com.demo.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/applications")
    public void applyForJob(@Valid @RequestBody ApplyForJobDto dto, Principal principal) {
        applicationService.applyForJob(dto, principal.getName());
    }

    @GetMapping("/myapplications")
    public List<ApplicationResponseDto> getMyApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        return applicationService.getMyApplications(principal.getName(), page, size);
    }
}
