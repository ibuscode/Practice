package com.InsuranceSystem.repository;

import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

    Optional<Proposal> findByQuoteQuoteId(int quoteId);

    List<Proposal> findByProposalStatus(ProposalStatus status);

    @Query("SELECT p FROM Proposal p WHERE p.officer.officerId = :officerId AND p.proposalStatus = :status")
    List<Proposal> findAssignedProposalsByOfficerId(@Param("officerId") int officerId,
                                                    @Param("status") ProposalStatus status);

    @Query("SELECT p FROM Proposal p " +
            "JOIN FETCH p.customer c " +
            "JOIN FETCH p.vehicle v " +
            "JOIN FETCH p.quote q " +
            "WHERE p.proposalId = :proposalId")
    Optional<Proposal> findProposalDetailsById(@Param("proposalId") int proposalId);

    // Get all proposals by customer ID
    @Query("SELECT p FROM Proposal p WHERE p.customer.customerId = :customerId")
    List<Proposal> findByCustomerId(@Param("customerId") int customerId);

    // Get all proposals by customer username
    @Query("SELECT p FROM Proposal p WHERE p.customer.user.username = :username")
    List<Proposal> findByCustomerUsername(@Param("username") String username);

    @Query("SELECT COUNT(p) FROM Proposal p WHERE p.customer.customerId = :customerId")
    long countByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT COUNT(p) FROM Proposal p WHERE p.customer.customerId = :customerId AND p.proposalStatus = 'SUBMITTED'")
    long countSubmittedByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT COUNT(p) FROM Proposal p WHERE p.officer.officerId = :officerId AND p.proposalStatus = :status")
    long countByOfficerIdAndStatus(@Param("officerId") int officerId, @Param("status") ProposalStatus status);
}
