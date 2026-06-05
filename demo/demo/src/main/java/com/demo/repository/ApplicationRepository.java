package com.demo.repository;

import com.demo.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Page<Application> findByJobSeekerId(Integer jobSeekerId, Pageable pageable);

    boolean existsByJobSeekerIdAndJobId(Integer jobSeekerId, Integer jobId);
}
