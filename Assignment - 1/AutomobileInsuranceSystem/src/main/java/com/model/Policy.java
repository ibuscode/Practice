package com.model;

import com.enums.StatusType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.DrbgParameters;
import java.time.Instant;
import java.time.LocalDate;
@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int policyId;

    @Column(nullable = false)
      private String policyNumber;
    @Enumerated(EnumType.STRING)
      private StatusType statusType;
    @CreationTimestamp
      private Instant proposalDate;
      private LocalDate startDate;
      private LocalDate endDate;
      @Column(nullable = false)
      private float premiumAmount;

      @ManyToOne
    private Customer customer;

      @ManyToOne
    private InsuranceTeam insuranceTeam;

      @ManyToOne
      private Vehicle vehicle;


    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Instant getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Instant proposalDate) {
        this.proposalDate = proposalDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(float premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyId=" + policyId +
                ", policyNumber='" + policyNumber + '\'' +
                ", statusType=" + statusType +
                ", proposalDate=" + proposalDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", premiumAmount=" + premiumAmount +
                '}';
    }
}
