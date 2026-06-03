package com.cms.mapper;

import com.cms.dto.IncidentDto;
import com.cms.dto.IncidentOfficerDto;
import com.cms.dto.IncidentRecDto;
import com.cms.model.Incident;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentMapper {

    public Incident mapDtoEntity(IncidentDto incidentDto) {
        Incident incident = new Incident();
        incident.setIncidentType(incidentDto.incidentType());
        incident.setProgressDetails(incidentDto.progressDetails());
        incident.setIncidentStatus(incidentDto.incidentStatus());
        return incident;
    }

    public IncidentRecDto mapEntityToDto(Page<Incident> pages) {
       long totalElements = pages.getTotalElements();
       int totalPages = pages.getTotalPages();
        List<Incident> list = pages.getContent();
        IncidentRecDto dto = new IncidentRecDto(
                totalElements,
                totalPages,
                list
        );
        return dto;    }
    public IncidentOfficerDto getDtoForEntity(Incident incident) {
        return new IncidentOfficerDto(
                incident.getId(),
                incident.getIncidentType(),
                incident.getIncidentStatus(),
                incident.getUpdatedAt(),
                incident.getOfficer().getId(),
                incident.getOfficer().getName(),
                incident.getOfficer().getUser().getUsername()
        );
    }
}
