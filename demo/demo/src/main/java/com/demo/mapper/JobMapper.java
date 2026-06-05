package com.demo.mapper;

import com.demo.dto.CreateJobReqDto;
import com.demo.dto.JobResponseDto;
import com.demo.model.Employer;
import com.demo.model.Job;

public class JobMapper {

    public static Job dtoToEntity(CreateJobReqDto dto, Employer employer) {
        Job job = new Job();
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setSalary(dto.salary());
        job.setEmployer(employer);
        return job;
    }

    public static JobResponseDto toDto(Job job) {
        return new JobResponseDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSalary(),
                job.getEmployer().getCompanyName()
        );
    }
}
