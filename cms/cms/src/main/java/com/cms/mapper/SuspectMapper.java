package com.cms.mapper;

import com.cms.dto.SuspectIncidentResDto;
import com.cms.model.Incident;
import com.cms.model.Suspect;
import org.springframework.stereotype.Component;

@Component
public class SuspectMapper {

    public static SuspectIncidentResDto dtoToEntity(Incident incident, Suspect suspect) {
        return new SuspectIncidentResDto(
                incident.getIncidentType(),
                incident.getIncidentStatus(),
                incident.getProgressDetails(),
                suspect.getName(),
                suspect.getContact(),
                suspect.getId()
        );
    }
}
