package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.CoverageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PolicyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String policyName;
    @Column(nullable = false)
    private String description;
    private double basePremium;
    @Enumerated(EnumType.STRING)
    private CoverageType coverageType;
    private boolean isActive;
}
