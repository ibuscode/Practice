package com.demo.dto;

public record EmployerRegisterRespDto(
        String username,
        String password,
        String companyName
) {
}
