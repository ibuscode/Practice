package com.InsuranceSystem.repository;

import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.model.Policy;
import com.InsuranceSystem.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

    List<Policy> findByProposalCustomerUserId(int userId);

    Optional<Policy> findByProposalProposalId(int proposalId);

    List<Policy> findByProposalCustomerCustomerId(int customerId);

    @Query("SELECT COUNT(p) FROM Policy p WHERE p.proposal.customer.customerId = :customerId AND p.policyStatus = 'ACTIVE'")
    long countActiveByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT COUNT(p) FROM Policy p WHERE p.policyStatus = :status")
    long countByPolicyStatus(@Param("status") PolicyStatus status);

    List<Policy> findByProposalVehicleVehicleId(int vehicleId);


}
