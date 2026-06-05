package com.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJobReqDto(

        @NotBlank
        @NotNull
        String title,
        @NotNull
        @NotBlank
        String description,
        @NotNull
        String location,
        Double salary

) {
}
