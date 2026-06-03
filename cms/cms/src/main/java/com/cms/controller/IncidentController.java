package com.cms.controller;

import com.cms.dto.IncidentDto;
import com.cms.dto.IncidentOfficerDto;
import com.cms.dto.IncidentRecDto;
import com.cms.dto.IncidentStatDto;
import com.cms.enums.IncidentStatus;
import com.cms.enums.IncidentType;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Incident;
import com.cms.service.IncidentService;
import com.cms.service.OfficerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * In controller if you are creating REST APIs
 * then add
 * @RestController annotation which is a combo of
 * @Controller & @ResponseBody
 * But if you are using this controller to load java UI(jsp or Thymeleaf)
 * then use only @Controller
 * */
@RestController
@AllArgsConstructor
public class IncidentController {


    private final IncidentService incidentService;
    private final OfficerService officerService;
    @GetMapping("/api/incident/all")
    public List<Incident> getAll() {
        return incidentService.getAll();
    }

    @PostMapping("/api/incident/add")
    public void addIncident(@Valid @RequestBody IncidentDto incidentDto) {
        incidentService.addIncident(incidentDto);
    }
    @PostMapping("/api/incident/add/v2/{officerId}")
    public void addIncidentWithOfficer(@Valid @RequestBody IncidentDto dto, @PathVariable int officerId) {
      //  officerService.getById(officerId);

    }

    public IncidentRecDto getAllv2(@RequestParam int page, @RequestParam int size) {
         return incidentService.getAllWithPagination(page, size);
    }

    @GetMapping("/api/incident/get-one/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {


            Incident incident = incidentService.getById(id);
            return ResponseEntity
                    .ok(incident);

    }
        @DeleteMapping("/api/incident/delete/{id}")
        public void deleteById(@PathVariable int id) {

                incidentService.deleteById(id);

        }
    @PutMapping("/api/incident/update/{id}")
    public void update(@PathVariable int id,
                                         @RequestBody Incident updatedIncident) {
            incidentService.update(id, updatedIncident);


        }
    @GetMapping("/api/incident/type")
    public List<Incident> getByIncidentType(@RequestParam IncidentType incidentType){
        return incidentService.getByIncidentType(incidentType);
    }
    @GetMapping("/api/incident/status")
    public List<Incident> getByIncidentStatus(@RequestParam IncidentStatus incidentStatus) {
        return incidentService.getByIncidentStatus(incidentStatus);
    }
   /* @GetMapping("/api/incident/get/officer/{officerId}")
    public List<IncidentOfficerDto> getIncidentByOfficerId(@PathVariable int officerId) {
        return incidentService.getIncidentByOfficerId(officerId);
    }*/

    @GetMapping("/api/incident/incident-type/stat")
    public IncidentStatDto incidentStat() {
        return incidentService.incidentStat();
    }

    }
