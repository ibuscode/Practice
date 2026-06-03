package com.cms.dto;

import java.time.LocalDate;

public record EmployeeProjectDto(
        LocalDate allotedDate,
        String roleDetails,
        int duration
) {
}
