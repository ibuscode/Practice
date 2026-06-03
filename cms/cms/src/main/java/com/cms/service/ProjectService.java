package com.cms.service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Project;
import com.cms.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {
private final ProjectRepository projectRepository;
public Project getById(int id) {
    return projectRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Invalid Id"));
}
}
