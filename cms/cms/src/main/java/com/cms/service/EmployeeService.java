package com.cms.service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Employee;
import com.cms.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid incident id"));
    }
}
