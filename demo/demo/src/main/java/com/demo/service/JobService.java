package com.demo.service;

import com.demo.dto.CreateJobReqDto;
import com.demo.dto.JobResponseDto;
import com.demo.exceptions.ResourceNotFoundException;
import com.demo.mapper.JobMapper;
import com.demo.model.Employer;
import com.demo.model.Job;
import com.demo.model.User;
import com.demo.repository.EmployeeRepository;
import com.demo.repository.JobRepository;
import com.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {

private final UserRepository userRepository;
private final EmployeeRepository employeeRepository;
private final JobRepository jobRepository;
    public void createJob(@Valid CreateJobReqDto dto, String username) {
        Employer employer = employeeRepository.findByUserUsername(username);
        Job job = JobMapper.dtoToEntity(dto,employer);
        jobRepository.save(job);


    }

    public List<JobResponseDto> getAllJobs(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<Job> jobs = jobRepository.findAll(pageable).getContent();

        return jobs.stream()
                .map(JobMapper::toDto)
                .toList();
    }


}
