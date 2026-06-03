package com.cms.service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Suspect;
import com.cms.repository.SuspectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SuspectService {
    private SuspectRepository suspectRepository;
    public  Suspect getById(int suspectId) {
        return suspectRepository.findById(suspectId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
    }
}
