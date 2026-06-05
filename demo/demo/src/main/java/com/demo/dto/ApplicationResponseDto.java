package com.demo.dto;

import java.time.LocalDateTime;

public record ApplicationResponseDto(
        Integer id,
        String jobTitle,
        String companyName,
        String jobLocation,
        Double salary
) {
}
