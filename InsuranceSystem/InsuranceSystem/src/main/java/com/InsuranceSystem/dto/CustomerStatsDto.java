package com.InsuranceSystem.dto;

import java.util.List;

public record CustomerStatsDto(
        String statType,
        List<String> labels,
        List<Long> values
) {
}
