package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    boolean existsByProposalProposalId(int proposalId);
    Optional<Payment> findByProposalProposalId(int proposalId);
}
