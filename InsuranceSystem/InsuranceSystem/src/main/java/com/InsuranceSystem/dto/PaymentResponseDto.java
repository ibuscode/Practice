package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentResponseDto(
        int paymentId,
        double amount,
        LocalDateTime paymentDate,
        PaymentStatus paymentStatus,
        int proposalId,
        String customerName
) {
}
