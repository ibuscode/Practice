package com.cms.service;

import com.cms.dto.EmployeeProjectDto;
import com.cms.mapper.EmployeeProjectMapper;
import com.cms.model.Employee;
import com.cms.model.EmployeeProject;
import com.cms.model.Project;
import com.cms.repository.EmployeeProjectRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeProjectService {
    private final EmployeeProjectRepository employeeProjectRepository;
private final EmployeeService employeeService;
private final ProjectService projectService;

    public void add(int employeeId, int projectId, @Valid EmployeeProjectDto dto) {

        Employee employee = employeeService.getById(employeeId);
        Project project = projectService.getById(projectId);
        EmployeeProject employeeProject = EmployeeProjectMapper.dtoToEntity(dto);
        employeeProject.setEmployee(employee);
        employeeProject.setProject(project);
        employeeProjectRepository.save(employeeProject);
    }
}
