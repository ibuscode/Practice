package com.cms.controller;

import com.cms.dto.SuspectStationResDto;
import com.cms.service.StationService;
import com.cms.service.suspectStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/api/station/by-incident/{incidentId}")
    public void getStationByIncidentId(@PathVariable int incidentId) {

        stationService.getStationByIncidentId(incidentId);

    }
   /* @GetMapping("/api/station/suspect/by-station/{stationId}")
    public SuspectStationResDto getAllSuspectsByStation(@PathVariable int stationId) {
        suspectStationService.getAllSuspectByStation(stationId);


    }*/
}
