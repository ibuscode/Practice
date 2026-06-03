package com.InsuranceSystem.model;

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
    private double premiumAmount;
    private double baseAmount;
    private double addonAmount;
    @Column(nullable = false)
    private LocalDate validUntil;
    @Enumerated(EnumType.STRING)
    private QuoteStatus quoteStatus;
    @CreationTimestamp
    private Instant generatedAt;
    @ManyToOne
    private Officer officer;
    @OneToOne
    private Proposal proposal;
}
