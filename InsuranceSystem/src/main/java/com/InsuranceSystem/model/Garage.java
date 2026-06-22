package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.GarageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int garageId;

    private String garageName;
    private String address;
    private String city;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private GarageStatus status;
}
