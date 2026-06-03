package com.InsuranceSystem.model;

import com.InsuranceSystem.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class ProposalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;
    private String fileName;
    private String filePath;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @CreationTimestamp
    private Instant uploadedAt;
    @ManyToOne
    private Proposal proposal;
}
