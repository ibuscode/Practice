package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.PaymentRequestDto;
import com.InsuranceSystem.dto.PaymentResponseDto;
import com.InsuranceSystem.enums.PaymentStatus;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Payment;
import com.InsuranceSystem.model.Proposal;

import java.time.LocalDateTime;

public class PaymentMapper {
    // Convert request + entities to Payment Entity
    public static Payment toEntity(PaymentRequestDto request, Customer customer, Proposal proposal) {
        Payment payment = new Payment();
        payment.setAmount(proposal.getQuote().getPremiumAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setCustomer(customer);
        payment.setProposal(proposal);
        return payment;
    }

    // Convert Payment Entity to Response DTO
    public static PaymentResponseDto toDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentStatus(),
                payment.getProposal().getProposalId(),
                payment.getCustomer().getName()
        );
    }
}
