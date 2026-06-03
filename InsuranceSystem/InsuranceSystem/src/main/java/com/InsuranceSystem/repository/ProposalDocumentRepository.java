package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.ProposalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalDocumentRepository extends JpaRepository<ProposalDocument, Integer> {

    @Query(
            """

                    SELECT pd FROM ProposalDocument pd\s
WHERE pd.proposal.proposalId = ?1
"""
    )
    List<ProposalDocument> findByProposalId(int proposalId);
}
