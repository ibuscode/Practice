package com.cms.service;

import com.cms.dto.*;
import com.cms.enums.IncidentStatus;
import com.cms.enums.IncidentType;
import com.cms.exception.ResourceNotFoundException;
import com.cms.mapper.IncidentMapper;
import com.cms.model.Incident;
import com.cms.model.Officer;
import com.cms.repository.IncidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;import java.util.List;

/*
To create a bean in spring boot we can use following annotations
for services class use @Service
for repositories , use @Repository
for all other classes including util use @Component
* */
@Service
@AllArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper ticketMapper;
    private final ListableBeanFactory listableBeanFactory;
    private final OfficerService officerService;

    public List<Incident> getAll() {

        return incidentRepository.findAll();
    }
    public void addIncident(IncidentDto incidentDto) {

        Incident incident = ticketMapper.mapDtoEntity(incidentDto);
        incidentRepository.save(incident);
    }

    public Incident getById(int id) {
         return incidentRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Invalid incident id"));

    }
    public void deleteById(int id) {
        getById(id);
        incidentRepository.deleteById(id);
    }

    public void update(int id, Incident updatedIncident) {
        Incident exisitngIncident = getById(id);
        // set the new values given to existing incident
        exisitngIncident.setIncidentStatus(updatedIncident.getIncidentStatus());
        exisitngIncident.setIncidentType(updatedIncident.getIncidentType());
        exisitngIncident.setProgressDetails(updatedIncident.getProgressDetails());
        incidentRepository.save(exisitngIncident);
    }
    public IncidentRecDto getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Incident> pages = incidentRepository.findAll(pageable);
        return ticketMapper.mapEntityToDto(pages);
    }
    public List<Incident> getByIncidentType(IncidentType incidentType) {
        return incidentRepository.findByIncidentType(incidentType);
    }
    public List<Incident> getByIncidentStatus(IncidentStatus incidentStatus) {
        return incidentRepository.findByIncidentStatus(incidentStatus);
    }

    public IncidentStatDto incidentStat() {
     List<IncidentStatJpqlDto> list = incidentRepository.incidentStat();
     List<String> names = list.stream()
             .map(IncidentStatJpqlDto::types)
             .toList();
     List<Long> numberList = list.stream()
             .map(IncidentStatJpqlDto::incidentCount)
             .toList();
     return new IncidentStatDto(
             "Number of Incidents for each type",
             names,
             numberList

     );
    }



  /*  public List<IncidentOfficerDto> getIncidentByOfficerId(int officerId) {
        Officer officer = officerService.getById(officerId);
        List<Incident> list = incidentRepository.findByOfficerId(officerId);

        return list
                .stream()
                .map(ticketMapper::getDtoForEntity)
                .toList();

    }*/
}
