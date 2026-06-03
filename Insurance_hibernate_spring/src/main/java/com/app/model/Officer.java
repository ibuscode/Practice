package com.app.model;

import jakarta.persistence.*;

@Entity
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officerId;

    @Column(nullable = false)
    private String officerName;
    @Column(nullable = false)
    private String email;
    @OneToOne
    private User user;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Officer() {}

    public Officer(String email, String officerName) {
        this.email = email;
        this.officerName = officerName;
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
