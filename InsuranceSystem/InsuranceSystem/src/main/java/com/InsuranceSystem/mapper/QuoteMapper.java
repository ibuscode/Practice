package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.QuoteResponseDto;
import com.InsuranceSystem.model.Quote;

public class QuoteMapper {

    public static QuoteResponseDto quoteToDto(Quote quote, double vehicleAgeAmount, double riskAmount) {
        return new QuoteResponseDto(
                quote.getQuoteId(),
                quote.getProposal().getProposalId(),
                quote.getPremiumAmount(),
                quote.getBaseAmount(),
                quote.getAddonAmount(),
                vehicleAgeAmount,
                riskAmount,
                quote.getValidUntil(),
                quote.getQuoteStatus(),
                quote.getGeneratedAt()
        );
    }
}
