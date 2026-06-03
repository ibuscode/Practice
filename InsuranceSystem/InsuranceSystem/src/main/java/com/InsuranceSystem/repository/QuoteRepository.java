package com.InsuranceSystem.repository;

import com.InsuranceSystem.enums.QuoteStatus;
import com.InsuranceSystem.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    Optional<Quote> findTopByProposalProposalIdOrderByGeneratedAtDesc(int proposalId);

    @Query(
            """
 SELECT q FROM Quote q
    WHERE q.validUntil < :date
    AND q.quoteStatus = :quoteStatus
"""
    )
    List<Quote> findByValid(LocalDate date, QuoteStatus quoteStatus);
    @Query("""
SELECT q FROM Quote q WHERE q.quoteId = ?1
""")
    Optional<Quote> findByIdOnly(@Param("quoteId") Long quoteId);
}

