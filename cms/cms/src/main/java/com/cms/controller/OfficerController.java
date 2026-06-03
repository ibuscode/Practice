package com.cms.controller;

import com.cms.dto.OfficerIncidentStatRespDto;
import com.cms.dto.OfficerReqDto;
import com.cms.service.OfficerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class OfficerController {

    private final OfficerService officerService;

   /* @GetMapping("/api/officer/by-station-head")
    public List<OfficerListDto> getOfficersByStationHead(@RequestParam String stationHeadUsername) {
         return officerService.getOfficerByStationHead(stationHeadUsername);
    }*/


    @PostMapping("/api/officer/add")
    public void postOfficer(@RequestBody OfficerReqDto officerReqDto) {
        officerService.postOfficer(officerReqDto);
    }

    @GetMapping("/api/officer/by-incident/stat")
    public OfficerIncidentStatRespDto incidentByOfficerStat() {
        return officerService.incidentByOfficerStat();
    }
    @PostMapping("/id/upload")
    public void upload(Principal principal,
                       @RequestParam("file") MultipartFile file) throws IOException {
        //file is the actual doc/image user is uploading.

        String username = principal.getName();
        officerService.upload(username, file);
    }
}
