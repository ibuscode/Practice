package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyId;
    @Column(nullable = false, unique = true)
    private String policyNumber;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyStatus policyStatus;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant issuedAt;
    @OneToOne
    private Proposal proposal;

}
