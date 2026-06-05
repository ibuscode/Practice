package com.demo.dto;

public record SeekerRegisterRespDto(
        String username,
        String password,
        String name,
        String resumeSummary
) {
}
