package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.*;
import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.mapper.ProposalMapper;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.model.Quote;
import com.InsuranceSystem.repository.OfficerRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import com.InsuranceSystem.repository.QuoteRepository;
import com.InsuranceSystem.utility.FileUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final QuoteRepository quoteRepository;
    private final OfficerRepository officerRepository;
    private static final String UPLOAD_LOC = "E:/Trainning/insurance_UI/public/Documents";


    public void uploadRcBook(String username, int quoteId, MultipartFile file) throws IOException {
        // Fetch Quote
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        FileUtility.validateFile(file);

        // Original filename
        String fileName = "rcbook_" + quoteId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Create upload path
        Path uploadPath = Paths.get(UPLOAD_LOC);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Destination path
        Path destinationPath = uploadPath.resolve(fileName);

        // Copy file
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Save to database with status
        Proposal proposal = getOrCreateProposal(quoteId);
        proposal.setRcBookPath(fileName);

        // Set status to SUBMITTED when first document is uploaded
        if (proposal.getProposalStatus() == null) {
            proposal.setProposalStatus(ProposalStatus.SUBMITTED);
        }

        proposalRepository.save(proposal);
    }


    public void uploadDrivingLicense(String username, int quoteId, MultipartFile file) throws IOException {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        FileUtility.validateFile(file);

        String fileName = "license_" + quoteId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get(UPLOAD_LOC);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path destinationPath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        Proposal proposal = getOrCreateProposal(quoteId);
        proposal.setDrivingLicensePath(fileName);

        // Set status to SUBMITTED if not already set
        if (proposal.getProposalStatus() == null) {
            proposal.setProposalStatus(ProposalStatus.SUBMITTED);
        }

        proposalRepository.save(proposal);
    }

    private Proposal getOrCreateProposal(int quoteId) {
        return proposalRepository.findByQuoteQuoteId(quoteId)
                .orElseGet(() -> {
                    Quote quote = quoteRepository.findById(quoteId)
                            .orElseThrow(() -> new RuntimeException("Quote not found"));

                    Proposal newProposal = new Proposal();
                    newProposal.setQuote(quote);
                    newProposal.setVehicle(quote.getVehicle());
                    newProposal.setCustomer(quote.getCustomer());
                    newProposal.setProposalStatus(ProposalStatus.SUBMITTED);

                    return proposalRepository.save(newProposal);  // ← only change, add .save()
                });
    }

    public ProposalListDto assignOfficer(@Valid AssignOfficerRequestDto request) {

        // Find proposal
        Proposal proposal = proposalRepository.findById(request.proposalId())
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // Find officer
        Officer officer = officerRepository.findById(request.officerId())
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        // Assign officer and update status
        proposal.setOfficer(officer);
        proposal.setProposalStatus(ProposalStatus.ASSIGNED);

        // Save
        Proposal savedProposal = proposalRepository.save(proposal);

        // Return DTO
        return ProposalMapper.toListDto(savedProposal);
    }

    public List<ProposalListDto> getSubmittedProposals() {
        List<Proposal> proposals = proposalRepository.findByProposalStatus(ProposalStatus.SUBMITTED);

        return proposals.stream()
                .map(ProposalMapper::toListDto)
                .collect(Collectors.toList());
    }

    public List<AssignedProposalDto> getAssignedProposalsForOfficer(String username) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        List<Proposal> proposals = proposalRepository.findAssignedProposalsByOfficerId(
                officer.getOfficerId(),
                ProposalStatus.ASSIGNED
        );

        return proposals.stream()
                .map(ProposalMapper::toAssignedDto)
                .collect(Collectors.toList());
    }

    public ProposalDetailsDto getProposalDetails(int proposalId) {
        Proposal proposal = proposalRepository.findProposalDetailsById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        return ProposalMapper.toDetailsDto(proposal);
    }

    public ProposalDetailsDto updateProposalStatus(int proposalId, String status) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        if(status.equalsIgnoreCase("APPROVED")) {
            proposal.setProposalStatus(ProposalStatus.APPROVED);
        } else if(status.equalsIgnoreCase("REJECTED")) {
            proposal.setProposalStatus(ProposalStatus.REJECTED);
        } else {
            throw new RuntimeException("Invalid status");
        }

        Proposal updatedProposal = proposalRepository.save(proposal);
        return ProposalMapper.toDetailsDto(updatedProposal);
    }
}
