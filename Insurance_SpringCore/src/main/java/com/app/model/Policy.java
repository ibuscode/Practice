package com.app.model;

import com.app.enums.StatusType;

import java.time.Instant;
import java.time.LocalDate;

public class Policy {
    private int policyId;
    private String policyNumber;
    private StatusType statusType;
    private Instant proposalDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private float premiumAmount;

    public Policy() {}

    public Policy(String policyNumber, StatusType statusType, Instant proposalDate, LocalDate startDate, LocalDate endDate, float premiumAmount) {
        this.policyNumber = policyNumber;
        this.statusType = statusType;
        this.proposalDate = proposalDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premiumAmount = premiumAmount;
    }

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
