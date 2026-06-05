package com.demo.dto;

public record JobResponseDto(
        Integer id,
        String title,
        String description,
        String location,
        Double salary,
        String companyName
) {
}
