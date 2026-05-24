package com.model;

import jakarta.persistence.*;

@Entity
public class InsuranceTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int officerId;

    @Column(nullable = false)
private String officerName;
    @Column(nullable = false)
private String email;

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "InsuranceTeam{" +
                "officerId=" + officerId +
                ", officerName='" + officerName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
