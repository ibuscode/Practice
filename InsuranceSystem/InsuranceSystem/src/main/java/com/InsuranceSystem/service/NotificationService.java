package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.QuoteEmailResponseDto;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.model.AddOn;
import com.InsuranceSystem.model.Quote;
import com.InsuranceSystem.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private QuoteRepository quoteRepository;
    private EmailService emailService;


    public QuoteEmailResponseDto sendQuoteEmail(Long quoteId) {
        // Fetch quote
        Quote quote = quoteRepository.findByIdOnly(quoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found with id: " + quoteId));

        try {
            // Get customer details
            String to = quote.getProposal().getCustomer().getEmail();
            String customerName = quote.getProposal().getCustomer().getName();

            // Build subject
            String subject = "Your Insurance Quote #" + quote.getQuoteId();

            // Build email body
            StringBuilder body = new StringBuilder();
            body.append("Dear ").append(customerName).append(",\n\n");
            body.append("Your insurance quote has been generated.\n");
            body.append("Quote ID: ").append(quote.getQuoteId()).append("\n");
            body.append("Policy Type: ").append(quote.getProposal().getPolicyType().getPolicyName()).append("\n");
            body.append("Vehicle: ").append(quote.getProposal().getVehicle().getModel())
                    .append(" ").append(quote.getProposal().getVehicle().getModel())
                    .append(" (").append(quote.getProposal().getVehicle().getRegNo()).append(")\n");
            body.append("\n--- Premium Breakdown ---\n");
            body.append("Base Premium: ₹").append(quote.getBaseAmount()).append("\n");

          /*  if (quote.getAddonAmount() != null && quote.getAddonAmount().doubleValue() > 0) {
                body.append("Add-ons: ");
                for (AddOn addon : quote.getProposal().getSelectedAddOns()) {
                    body.append(addon.getName()).append(", ");
                }
                body.append("\nAdd-on Total: ₹").append(quote.getAddonAmount()).append("\n");
            }

            body.append("Total Premium: ₹").append(quote.getPremiumAmount()).append("\n");
            body.append("Valid Until: ").append(quote.getValidUntil()).append("\n");
            body.append("\nPlease make the payment before the expiry date to activate your policy.\n");
            body.append("Thank you for choosing us!\n");*/

            // Send email
           // emailService.sendEmail();

            // Return success response
            return new QuoteEmailResponseDto(quoteId, to, "SENT", LocalDateTime.now(), "Quote email sent successfully");

        } catch (Exception e) {
            return new QuoteEmailResponseDto(quoteId, null, "FAILED", LocalDateTime.now(), "Error: " + e.getMessage());
        }
    }
}

