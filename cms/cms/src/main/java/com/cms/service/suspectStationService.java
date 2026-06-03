package com.cms.service;

import com.cms.dto.SuspectStationResDto;
import com.cms.model.Suspect;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class suspectStationService {

    private final SuspectService suspectService;
   // public  SuspectStationResDto getAllSuspectByStation(int stationId) {
        //Suspect suspect = suspectService.getById(suspectId);
    //}


}
