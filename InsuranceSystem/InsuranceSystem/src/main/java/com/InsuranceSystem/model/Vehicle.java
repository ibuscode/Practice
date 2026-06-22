package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehicleId;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @Column(nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false, unique = true)
    private String regNo;
    @ManyToOne
    private Customer customer;
}
