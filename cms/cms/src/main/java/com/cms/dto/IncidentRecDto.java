package com.cms.dto;

import com.cms.model.Incident;

import java.util.List;

public record IncidentRecDto(
        long totalRecords,
        int totalPages,
        List<Incident> data
) {
}
