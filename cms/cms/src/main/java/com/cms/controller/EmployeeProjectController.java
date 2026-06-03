package com.cms.controller;

import com.cms.dto.EmployeeProjectDto;
import com.cms.service.EmployeeProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmployeeProjectController {

    private final EmployeeProjectService employeeProjectService;
    @PostMapping("/api/employee/project/add/{employeeId}/{projectId}")
    public void add(@PathVariable int employeeId, @PathVariable int projectId, @Valid @RequestBody EmployeeProjectDto dto) {
        employeeProjectService.add(employeeId, projectId, dto);

    }
}
