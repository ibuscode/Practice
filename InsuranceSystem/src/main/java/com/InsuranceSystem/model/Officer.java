package com.InsuranceSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
@Entity
@Getter
@Setter
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officerId;
    @Column(nullable = false)
    private String name;
    private String email;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
    @OneToOne
    private User user;
}
