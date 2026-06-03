package com.cms.mapper;

import com.cms.dto.IncidentStationDto;
import com.cms.model.Officer;
import com.cms.model.Station;

public class IncidentStationMapper {

    public static IncidentStationDto dtoToEntity(Station station, Officer officer) {
        return new IncidentStationDto(
                station.getId(),
                station.getStationTitle(),
                station.getStationHead().getName(),
                officer.getName()
        );
    }
}
