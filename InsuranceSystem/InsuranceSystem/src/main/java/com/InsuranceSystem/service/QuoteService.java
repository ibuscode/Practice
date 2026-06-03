package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.QuoteRequestDto;
import com.InsuranceSystem.dto.QuoteResponseDto;
import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.enums.QuoteStatus;
import com.InsuranceSystem.enums.VehicleType;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.QuoteMapper;
import com.InsuranceSystem.model.AddOn;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.model.Quote;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.repository.OfficerRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import com.InsuranceSystem.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ProposalRepository proposalRepository;
    private final OfficerRepository officerRepository;
    private final UserService userService;
    private final EmailService emailService;

    public QuoteResponseDto generateQuote(QuoteRequestDto dto, String officerUsername) {
        Proposal proposal = proposalRepository.findById(dto.proposalId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Proposal Id"));

        if (proposal.getProposalStatus() != ProposalStatus.APPROVED) {
            throw new RuntimeException("Proposal must be approved before quote generation");
        }

        User officerUser = (User) userService.loadUserByUsername(officerUsername);
        Officer officer = officerRepository.findByUserId(officerUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Officer profile not found"));

        double baseAmount = proposal.getPolicyType().getBasePremium();
        double addonAmount = proposal.getSelectedAddOns()
                .stream()
                .mapToDouble(AddOn::getPrice)
                .sum();
        double vehicleAgeAmount = getVehicleAgeAmount(proposal);
        double riskAmount = getRiskAmount(proposal);

        Quote quote = new Quote();
        quote.setProposal(proposal);
        quote.setOfficer(officer);
        quote.setBaseAmount(baseAmount);
        quote.setAddonAmount(addonAmount);
        quote.setPremiumAmount(baseAmount + addonAmount + vehicleAgeAmount + riskAmount);
        quote.setValidUntil(LocalDate.now().plusDays(7));
        quote.setQuoteStatus(QuoteStatus.PENDING);
        quoteRepository.save(quote);

        emailService.sendEmail(proposal.getCustomer().getEmail(), "Your quote has been generated.");
        return QuoteMapper.quoteToDto(quote, vehicleAgeAmount, riskAmount);
    }

    public QuoteResponseDto getLatestQuote(int proposalId, String username) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Proposal Id"));

        User loginUser = (User) userService.loadUserByUsername(username);
        if (loginUser.getRole().toString().equals("CUSTOMER")
                && proposal.getCustomer().getUser().getId() != loginUser.getId()) {
            throw new RuntimeException("You can see only your quote");
        }

        Quote quote = quoteRepository.findTopByProposalProposalIdOrderByGeneratedAtDesc(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found"));

        return QuoteMapper.quoteToDto(quote, getVehicleAgeAmount(proposal), getRiskAmount(proposal));
    }

    public QuoteResponseDto expireQuote(int id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Quote Id"));

        quote.setQuoteStatus(QuoteStatus.EXPIRED);
        quoteRepository.save(quote);

        return QuoteMapper.quoteToDto(quote, getVehicleAgeAmount(quote.getProposal()), getRiskAmount(quote.getProposal()));
    }

    public void expireOldQuotes() {
        List<Quote> quotes = quoteRepository.findByValid(LocalDate.now(), QuoteStatus.PENDING);

        for (Quote quote : quotes) {
            quote.setQuoteStatus(QuoteStatus.EXPIRED);
            quoteRepository.save(quote);
        }
    }

    private double getVehicleAgeAmount(Proposal proposal) {
        return proposal.getVehicle().getAgeOfVehicle() * 100;
    }

    private double getRiskAmount(Proposal proposal) {
        VehicleType vehicleType = proposal.getVehicle().getVehicleType();

        if (vehicleType == VehicleType.TRUCK) {
            return 1000;
        }
        if (vehicleType == VehicleType.CAMPERVAN) {
            return 700;
        }
        return 400;
    }
}
