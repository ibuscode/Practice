package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proposalId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    private String remarks;
    private String requestDetails;
    private LocalDate coverageStartDate;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant submittedAt;
    @UpdateTimestamp
    private Instant updatedAt;
    @ManyToOne
    private Officer officer;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private PolicyType policyType;

}
