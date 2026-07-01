package com.InsuranceSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProposalAddon {
    @Id
    @GeneratedValue
    private Long id;

    String remarks;

    @ManyToOne
    private Proposal proposal;

    @ManyToOne
    private AddOn addOn;
}
