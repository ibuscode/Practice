package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

    List<Policy> findByProposalCustomerUserId(int userId);
}
