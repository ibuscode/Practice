package com.cms.dto;

import java.util.List;

public record IncidentStatDto(
        String title,
        List<String> label,
        List<Long> data
) {
}
