package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.ClaimStatus;
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
    private LocalDate claimDate=LocalDate.now();
    private LocalDate incidentDate;
    private String description;
    private double claimedAmount;
    private double approvedAmount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;
    @UpdateTimestamp
    private Instant updatedAt;
    @ManyToOne
    private Policy policy;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Officer officer;
}
