package com.cms.mapper;

import com.cms.dto.SuspectStationResDto;
import com.cms.model.Incident;
import com.cms.model.Station;
import com.cms.model.Suspect;

public class suspectStationMapper {

    public static SuspectStationResDto dtoToEntity(Suspect suspect, Incident incident) {
        return new SuspectStationResDto(
                suspect.getName(),
                suspect.getContact(),
                suspect.getCity(),
                incident.getIncidentType(),
                incident.getIncidentStatus(),
                incident.getOfficer().getName(),
                incident.getOfficer().getStation().getStationTitle()

        );
    }
}
