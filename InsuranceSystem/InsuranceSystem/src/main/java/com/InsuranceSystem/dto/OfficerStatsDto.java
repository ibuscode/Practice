package com.InsuranceSystem.dto;

import java.util.List;

public record OfficerStatsDto(
        String statType,
        List<String> proposalLabels,
        List<Long> proposalValues,
        List<String> claimLabels,
        List<Long> claimValues
) {}