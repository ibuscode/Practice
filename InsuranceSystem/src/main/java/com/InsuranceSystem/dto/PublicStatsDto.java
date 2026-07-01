package com.InsuranceSystem.dto;

import java.util.List;

public record PublicStatsDto(
        String statType,
        List<String> labels,
        List<Long> values
) {
}
