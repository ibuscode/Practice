package com.cms.dto;

import com.cms.enums.IncidentStatus;
import com.cms.enums.IncidentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IncidentDto(
        @NotNull(message = "This field is Mandatory")
         IncidentType incidentType,
        @NotNull(message = "This field is Mandatory")
                @NotBlank
                 String progressDetails,
                @NotNull
                 IncidentStatus incidentStatus
) {
}
