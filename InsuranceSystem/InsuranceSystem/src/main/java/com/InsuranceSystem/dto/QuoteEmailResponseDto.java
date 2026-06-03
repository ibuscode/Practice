package com.InsuranceSystem.dto;

import java.time.LocalDateTime;

public record QuoteEmailResponseDto(
        Long quoteId,
        String recipientEmail,
        String status,
        LocalDateTime sentAt,
        String message
) {
}
