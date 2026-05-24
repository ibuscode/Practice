package com.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quoteId;
    @CreationTimestamp
    private Instant generatedDate;

    @OneToOne
    private Policy policy;

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public Instant getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Instant generatedDate) {
        this.generatedDate = generatedDate;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", generatedDate=" + generatedDate +
                '}';
    }
}
