package com.demo.controller;

import com.demo.dto.CreateJobReqDto;
import com.demo.dto.JobResponseDto;
import com.demo.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/api/jobs/create-by-employer")
    private void createJob(@Valid @RequestBody CreateJobReqDto dto, Principal principal) {
        String username = principal.getName();
        jobService.createJob(dto, username);
    }

    @GetMapping
    public List<JobResponseDto> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        return jobService.getAllJobs(page, size);
    }

}
