package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.OfficerDto;
import com.InsuranceSystem.service.OfficerService;
import com.InsuranceSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class AdminController {

    private final UserService userService;
    private final OfficerService officerService;

    @PostMapping("/api/admin/officers")
    public void createOfficer(@Valid @RequestBody OfficerDto dto) {
         officerService.createOfficer(dto);
    }



}
