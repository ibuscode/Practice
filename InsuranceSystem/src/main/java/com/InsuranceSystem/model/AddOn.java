package com.InsuranceSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AddOn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addonId;
    @Column(nullable = false)
    private String addonName;
    private String description;
    @Column(nullable = false)
    private double price;
    @ManyToOne
    private PolicyType policyType;
}
