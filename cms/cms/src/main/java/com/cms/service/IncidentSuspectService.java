package com.cms.service;

import com.cms.dto.IncidentSuspectReqDto;
import com.cms.model.Incident;
import com.cms.model.Suspect;
import com.cms.repository.IncidentSuspectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IncidentSuspectService {

    private final IncidentSuspectRepository incidentSuspectRepository;
    private final IncidentService incidentService;
    public void add(int incidentId, int suspectId, IncidentSuspectReqDto dto) {
       Incident incident =  incidentService.getById(incidentId);
        //Suspect suspect = incidentSuspectRepository.getBySuspectId(suspectId);




    }
}
