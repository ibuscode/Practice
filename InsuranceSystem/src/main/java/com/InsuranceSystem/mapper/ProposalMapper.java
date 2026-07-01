package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.AssignedProposalDto;
import com.InsuranceSystem.dto.CustomerProposalDto;
import com.InsuranceSystem.dto.ProposalDetailsDto;
import com.InsuranceSystem.dto.ProposalListDto;
import com.InsuranceSystem.model.Proposal;

public class ProposalMapper {

    public static ProposalListDto toListDto(Proposal proposal) {
        return new ProposalListDto(
                proposal.getProposalId(),
                proposal.getCustomer() != null ? proposal.getCustomer().getName() : "Unknown",  // ← null check
                proposal.getVehicle() != null ? proposal.getVehicle().getVehicleType().toString() : "Unknown",  // ← null check
                proposal.getQuote().getPremiumAmount(),
                proposal.getProposalStatus(),
                proposal.getOfficer() != null ? proposal.getOfficer().getName() : null
        );
    }

    public static AssignedProposalDto toAssignedDto(Proposal proposal) {
        String vehicleName = proposal.getVehicle() != null
                ? proposal.getVehicle().getManufacturer() + " " + proposal.getVehicle().getModel()
                : "Unknown";

        return new AssignedProposalDto(
                proposal.getProposalId(),
                proposal.getCustomer() != null ? proposal.getCustomer().getName() : "Unknown",
                vehicleName,
                proposal.getProposalStatus()
        );
    }

    public static ProposalDetailsDto toDetailsDto(Proposal proposal) {
        return new ProposalDetailsDto(
                proposal.getProposalId(),
                proposal.getCustomer() != null ? proposal.getCustomer().getName() : "Unknown",
                proposal.getVehicle() != null ? proposal.getVehicle().getVehicleType().toString() : "Unknown",
                proposal.getVehicle() != null ? proposal.getVehicle().getManufacturer() : "Unknown",
                proposal.getVehicle() != null ? proposal.getVehicle().getModel() : "Unknown",
                proposal.getVehicle() != null ? proposal.getVehicle().getRegNo() : "Not Available",
                proposal.getQuote() != null ? proposal.getQuote().getCoverageAmount() : 0,
                proposal.getQuote() != null ? proposal.getQuote().getPremiumAmount() : 0,
                proposal.getProposalStatus(),
                proposal.getRcBookPath(),
                proposal.getDrivingLicensePath()
        );
    }

    public static CustomerProposalDto toCustomerDto(Proposal proposal, boolean isPaid) {

        String vehicleName;
        if (proposal.getVehicle() != null) {
            vehicleName = proposal.getVehicle().getManufacturer() + " " + proposal.getVehicle().getModel();
        } else {
            vehicleName = "Unknown";
        }

        double premiumAmount;
        if (proposal.getQuote() != null) {
            premiumAmount = proposal.getQuote().getPremiumAmount();
        } else {
            premiumAmount = 0;
        }

        return new CustomerProposalDto(
                proposal.getProposalId(),
                vehicleName,
                premiumAmount,
                proposal.getProposalStatus(),
                isPaid ? "PAID" : "PENDING"
        );
    }
}
