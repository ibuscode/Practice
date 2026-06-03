package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.OfficerDto;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/api/v1/admin/officers")
    public Officer createOfficer(@Valid @RequestBody OfficerDto dto) {
        return userService.createOfficer(dto);
    }
}
