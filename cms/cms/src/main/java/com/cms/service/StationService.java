package com.cms.service;

import com.cms.dto.IncidentStationDto;
import com.cms.mapper.IncidentStationMapper;
import com.cms.model.Incident;
import com.cms.model.Officer;
import com.cms.model.Station;
import com.cms.repository.StationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final IncidentService incidentService;
    private final OfficerService officerService;
    public IncidentStationDto getStationByIncidentId(int incidentId) {
        Incident incident  = incidentService.getById(incidentId);
        Station station = stationRepository.getStationByIncidentId(incidentId);
        Officer officer = officerService.getByIncidentId(incidentId);
        return IncidentStationMapper.dtoToEntity(station, officer);


    }
}
