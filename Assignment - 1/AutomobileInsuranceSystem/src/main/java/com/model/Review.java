package com.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    private Policy policy;

    @ManyToOne
    private InsuranceTeam insuranceTeam;

    @Column(nullable = false)
    private String action;

    @Column(length = 1000)
    private String comments;

    private LocalDate reviewDate;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public InsuranceTeam getInsuranceTeam() {
        return insuranceTeam;
    }

    public void setInsuranceTeam(InsuranceTeam insuranceTeam) {
        this.insuranceTeam = insuranceTeam;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", action='" + action + '\'' +
                ", comments='" + comments + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
