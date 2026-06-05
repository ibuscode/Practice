package com.demo.service;

import com.demo.dto.ApplicationResponseDto;
import com.demo.dto.ApplyForJobDto;
import com.demo.mapper.ApplicationMapper;
import com.demo.model.Application;
import com.demo.model.Job;
import com.demo.model.JobSeeker;
import com.demo.model.User;
import com.demo.repository.ApplicationRepository;
import com.demo.repository.JobRepository;
import com.demo.repository.JobSeekerRepository;
import com.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;
    public void applyForJob(@Valid ApplyForJobDto dto, String username) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(jobSeeker == null) {
            throw new RuntimeException("Only job seekers can apply for jobs");
        }

        Job job = jobRepository.findById(dto.jobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        boolean alreadyApplied = applicationRepository.existsByJobSeekerIdAndJobId(jobSeeker.getId(), job.getId());
        if(alreadyApplied) {
            throw new RuntimeException("You have already applied for this job");
        }

        Application application = ApplicationMapper.toEntity(job, jobSeeker);
        applicationRepository.save(application);
    }

    public List<ApplicationResponseDto> getMyApplications(String username, int page, int size) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));



        Pageable pageable = PageRequest.of(page, size);

        List<Application> applications = applicationRepository
                .findByJobSeekerId(jobSeeker.getId(), pageable)
                .getContent();

        return applications.stream()
                .map(ApplicationMapper::toDto)
                .toList();
    }

}
