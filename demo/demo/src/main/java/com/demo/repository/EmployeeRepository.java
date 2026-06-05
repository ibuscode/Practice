package com.demo.repository;

import com.demo.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employer, Integer> {
    Employer findByUserUsername(String username);

}
