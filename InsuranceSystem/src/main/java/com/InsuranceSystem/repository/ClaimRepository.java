package com.InsuranceSystem.repository;

import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.model.Claim;
import com.InsuranceSystem.model.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    List<Claim> findByCustomerCustomerId(int customerId);

    List<Claim> findByPolicyPolicyId(int policyId);

    @Query("SELECT c FROM Claim c WHERE c.policy = :policy AND c.claimStatus IN :statuses")
    List<Claim> findByPolicyAndClaimStatusIn(@Param("policy") Policy policy, @Param("statuses") List<ClaimStatus> statuses);

    boolean existsByPolicyAndClaimStatusIn(Policy policy, List<ClaimStatus> statuses);

    Optional<Claim> findByClaimIdAndCustomerCustomerId(int claimId, int customerId);

    @Query("SELECT c FROM Claim c " +
            "JOIN c.policy p " +
            "JOIN p.proposal pr " +
            "WHERE pr.officer.officerId = :officerId")
    Page<Claim> findClaimsByOfficerId(@Param("officerId") int officerId, Pageable pageable);

    @Query("SELECT c FROM Claim c " +
            "JOIN c.policy p " +
            "JOIN p.proposal pr " +
            "WHERE pr.officer.officerId = :officerId " +
            "AND c.claimStatus = :status")
    Page<Claim> findClaimsByOfficerIdAndStatus(@Param("officerId") int officerId,
                                               @Param("status") ClaimStatus status,
                                               Pageable pageable);

    @Query("SELECT c FROM Claim c " +
            "JOIN FETCH c.policy p " +
            "JOIN FETCH p.proposal pr " +
            "JOIN FETCH pr.customer " +
            "LEFT JOIN FETCH c.garage g " +
            "WHERE c.claimId = :claimId")
    Optional<Claim> findClaimWithDetails(@Param("claimId") int claimId);

    @Query("SELECT COUNT(c) FROM Claim c WHERE c.customer.customerId = :customerId")
    long countByCustomerCustomerId(@Param("customerId") int customerId);

    @Query("SELECT COUNT(c) FROM Claim c WHERE c.claimStatus = :status")
    long countByClaimStatus(@Param("status") ClaimStatus status);

    @Query("SELECT COUNT(c) FROM Claim c WHERE c.officer.officerId = :officerId AND c.claimStatus = :status")
    long countByOfficerIdAndStatus(@Param("officerId") int officerId, @Param("status") ClaimStatus status);

    @Query("SELECT COUNT(c) FROM Claim c WHERE c.officer.officerId = :officerId AND c.claimStatus IN :statuses")
    long countByOfficerIdAndStatuses(@Param("officerId") int officerId, @Param("statuses") List<ClaimStatus> statuses);
}
