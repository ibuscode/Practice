package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.ClaimType;
import com.InsuranceSystem.enums.PolicyType;
import com.InsuranceSystem.enums.QuoteStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quoteId;

    private double coverageAmount;
    private double premiumAmount;
    private double baseAmount;
    @Column(nullable = false)
    private LocalDate validUntil;
    @Enumerated(EnumType.STRING)
    private QuoteStatus quoteStatus;
    @CreationTimestamp
    private Instant generatedAt;
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    @Enumerated(EnumType.STRING)
    private ClaimType claimType;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Vehicle vehicle;

}
