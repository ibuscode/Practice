package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.ClaimType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;
    @Column(nullable = false)
    private LocalDate incidentDate;
    private String claimReason;
    private double claimAmount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;
    @Enumerated(EnumType.STRING)
    private ClaimType claimType;

    private String officerRemarks;
    // REIMBURSEMENT fields
    private Double repairBillAmount;
    private String receiptPath;
    @UpdateTimestamp
    private Instant createdAt;
    @ManyToOne
    private Policy policy;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Officer officer;
    @ManyToOne
    private Garage garage;
}
