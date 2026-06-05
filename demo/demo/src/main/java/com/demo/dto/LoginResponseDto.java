package com.demo.dto;

public record LoginResponseDto(
        int id,
        String username,
        String role
) {
}
