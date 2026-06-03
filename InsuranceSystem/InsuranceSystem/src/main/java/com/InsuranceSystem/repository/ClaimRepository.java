package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    List<Claim> findByCustomerUserIdOrderByClaimDateDesc(int userId);

    List<Claim> findByPolicyPolicyId(int policyId);
}
