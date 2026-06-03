package com.cms.dto;

import com.cms.enums.IncidentStatus;
import com.cms.enums.IncidentType;

import java.time.Instant;

public record IncidentOfficerDto(
        int incidentId,
        IncidentType incidentType,
        IncidentStatus incidentStatus,
        Instant updatedAt,
        int officerId,
        String officerName,
        String userName

) {
}
