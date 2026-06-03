package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.AddOnDto;
import com.InsuranceSystem.dto.PolicyTypeDto;
import com.InsuranceSystem.dto.ProposalDocumentDto;
import com.InsuranceSystem.dto.ProposalResponseDto;
import com.InsuranceSystem.model.AddOn;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.model.ProposalDocument;

import java.util.List;

public class ProposalMapper {

    public static ProposalResponseDto proposalToDto(Proposal proposal) {
        List<AddOnDto> addOns = proposal.getSelectedAddOns()
                .stream()
                .map(PolicyTypeMapper::addOnToDto)
                .toList();

        PolicyTypeDto policyTypeDto = PolicyTypeMapper.policyTypeToDto(
                proposal.getPolicyType(),
                proposal.getSelectedAddOns()
        );

        return new ProposalResponseDto(
                proposal.getProposalId(),
                proposal.getProposalStatus(),
                proposal.getRemarks(),
                proposal.getRequestDetails(),
                proposal.getCoverageStartDate(),
                proposal.getSubmittedAt(),
                proposal.getUpdatedAt(),
                UserMapper.customerToProfileDto(proposal.getCustomer()),
                VehicleMapper.entityToDto(proposal.getVehicle()),
                policyTypeDto,
                addOns
        );
    }

    public static ProposalDocumentDto documentToDto(ProposalDocument document) {
        return new ProposalDocumentDto(
                document.getDocumentId(),
                document.getFileName(),
                document.getFilePath(),
                document.getDocumentType(),
                document.getUploadedAt()
        );
    }
}
