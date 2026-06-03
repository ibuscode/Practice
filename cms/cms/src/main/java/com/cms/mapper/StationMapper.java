package com.cms.mapper;

import com.cms.dto.StationRespDto;
import com.cms.model.Incident;
import com.cms.model.Station;

public class StationMapper {
    public static StationRespDto entityToDto(Station station) {

        return new StationRespDto(
                station.getId(),
                station.getStationTitle(),
                station.getStationHead().getName()
                //station.

        );

    }
}
