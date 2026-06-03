package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ProposalDocumentDto(
        int documentId,
        @NotBlank(message = "file name is mandatory")
        String fileName,
        @NotBlank(message = "file path is mandatory")
        String filePath,
        @NotNull(message = "document type is mandatory")
        DocumentType documentType,
        @NotNull(message = "uploaded at is mandatory")
        Instant uploadedAt
) {
}
