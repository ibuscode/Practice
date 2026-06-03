package com.InsuranceSystem.repository;

import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

    List<Proposal> findByCustomerUserIdOrderBySubmittedAtDesc(int userId);

    @Query("""
            select p from Proposal p
            where (:status is null or p.proposalStatus = :status)
            and (:dateFrom is null or p.submittedAt >= :dateFrom)
            and (:dateTo is null or p.submittedAt <= :dateTo)
            order by p.submittedAt desc
            """)
    Page<Proposal> findAllWithFilters(
            @Param("status") ProposalStatus status,
            @Param("dateFrom") Instant dateFrom,
            @Param("dateTo") Instant dateTo,
            Pageable pageable);

}
