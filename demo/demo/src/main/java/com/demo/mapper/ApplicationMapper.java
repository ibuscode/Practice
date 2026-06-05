package com.demo.mapper;

import com.demo.dto.ApplicationResponseDto;
import com.demo.model.Application;
import com.demo.model.Job;
import com.demo.model.JobSeeker;

import java.time.LocalDateTime;

public class ApplicationMapper {
    public static Application toEntity(Job job, JobSeeker jobSeeker) {
        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(jobSeeker);
        return application;
    }

    public static ApplicationResponseDto toDto(Application application) {
        return new ApplicationResponseDto(
                application.getId(),
                application.getJob().getTitle(),
                application.getJob().getEmployer().getCompanyName(),
                application.getJob().getLocation(),
                application.getJob().getSalary()
        );
    }
}
