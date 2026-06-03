package com.cms.mapper;

import com.cms.dto.EmployeeProjectDto;
import com.cms.model.EmployeeProject;

public class EmployeeProjectMapper {

    public static EmployeeProject dtoToEntity(EmployeeProjectDto dto) {

        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setAllottedDate(dto.allotedDate());
        employeeProject.setRoleDetails(dto.roleDetails());
        employeeProject.setDuration(dto.duration());
        return employeeProject;
    }
}
