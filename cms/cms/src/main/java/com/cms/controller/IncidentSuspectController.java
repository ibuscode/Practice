package com.cms.controller;

import com.cms.dto.IncidentSuspectReqDto;
import com.cms.service.IncidentService;
import com.cms.service.IncidentSuspectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class IncidentSuspectController {

    private final IncidentSuspectService incidentSuspectService;
    private final IncidentService incidentService;

   /* @PostMapping("/api/incident/suspect/add/{incidentId}/{suspectId}")
    public void add(@PathVariable int incidentId, @PathVariable int suspectId, @Valid @RequestBody IncidentSuspectReqDto dto) {

        incidentService.add(incidentId, suspectId, dto);
    }*/

   /* @GetMapping("/api/by-incident/{incidentId}")
    public List<SuspectIncidentResDto> getAllSuspectByIncident(@PathVariable int incidentId)
    {

    }*/
}
