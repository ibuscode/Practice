package com.InsuranceSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    @Column(nullable = false)
    private String name;
    private String email;
    @Column(length = 1000)
    private String address;
    @Column(nullable = false)
    private Date dateOfBirth;
    @Column(nullable = false)
    private String phoneNumber;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @OneToOne
    private User user;

}
