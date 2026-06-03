package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.ProposalDocumentDto;
import com.InsuranceSystem.dto.ProposalRequestDto;
import com.InsuranceSystem.dto.ProposalResponseDto;
import com.InsuranceSystem.dto.ProposalStatusDto;
import com.InsuranceSystem.dto.QuoteRequestDto;
import com.InsuranceSystem.enums.DocumentType;
import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.enums.Role;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.ProposalMapper;
import com.InsuranceSystem.model.AddOn;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.PolicyType;
import com.InsuranceSystem.model.Proposal;
import com.InsuranceSystem.model.ProposalDocument;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.model.Vehicle;
import com.InsuranceSystem.repository.AddOnRepository;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.PolicyTypeRepository;
import com.InsuranceSystem.repository.ProposalDocumentRepository;
import com.InsuranceSystem.repository.ProposalRepository;
import com.InsuranceSystem.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final ProposalDocumentRepository proposalDocumentRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final PolicyTypeRepository policyTypeRepository;
    private final AddOnRepository addOnRepository;
    private final UserService userService;
    private final QuoteService quoteService;
    private final EmailService emailService;

    public ProposalResponseDto create(ProposalRequestDto dto, String username) {
        User user = (User) userService.loadUserByUsername(username);
        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer profile not found"));

        if (dto.coverageStartDate() == null || !dto.coverageStartDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Coverage start date must be future date");
        }

        Vehicle vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Vehicle Id"));

        if (vehicle.getCustomer().getCustomerId() != customer.getCustomerId()) {
            throw new RuntimeException("You can submit proposal only for your vehicle");
        }

        PolicyType policyType = policyTypeRepository.findById(dto.policyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Policy Type Id"));

        List<AddOn> selectedAddOns = new ArrayList<>();
        if (dto.selectedAddOnIds() != null) {
            selectedAddOns = addOnRepository.findAllById(dto.selectedAddOnIds());
        }

        Proposal proposal = new Proposal();
        proposal.setCustomer(customer);
        proposal.setVehicle(vehicle);
        proposal.setPolicyType(policyType);
        proposal.setSelectedAddOns(selectedAddOns);
        proposal.setCoverageStartDate(dto.coverageStartDate());
        proposal.setRemarks(dto.remarks());
        proposal.setProposalStatus(ProposalStatus.SUBMITTED);
        proposalRepository.save(proposal);

        return ProposalMapper.proposalToDto(proposal);
    }

    public List<ProposalResponseDto> getByUser(int userId, String username) {
        User loginUser = (User) userService.loadUserByUsername(username);

        if (loginUser.getRole() == Role.CUSTOMER && loginUser.getId() != userId) {
            throw new RuntimeException("You can see only your proposals");
        }

        return proposalRepository.findByCustomerUserIdOrderBySubmittedAtDesc(userId)
                .stream()
                .map(ProposalMapper::proposalToDto)
                .toList();
    }

    public ProposalResponseDto getById(int id, String username) {
        Proposal proposal = getProposal(id);
        checkProposalAccess(proposal, username);
        return ProposalMapper.proposalToDto(proposal);
    }

    public Page<ProposalResponseDto> getAll(ProposalStatus status, LocalDate dateFrom, LocalDate dateTo, int page, int size) {
        Instant from = null;
        Instant to = null;

        if (dateFrom != null) {
            from = dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        if (dateTo != null) {
            to = dateTo.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        }

        return proposalRepository.findAllWithFilters(status, from, to, PageRequest.of(page, size))
                .map(ProposalMapper::proposalToDto);
    }

    public ProposalResponseDto approve(int id, String officerUsername) {
        Proposal proposal = getProposal(id);
        proposal.setProposalStatus(ProposalStatus.APPROVED);
        proposalRepository.save(proposal);

        emailService.sendEmail(proposal.getCustomer().getEmail(), "Your proposal has been approved. Quote will be generated shortly.");
        quoteService.generateQuote(new QuoteRequestDto(id), officerUsername);

        return ProposalMapper.proposalToDto(proposal);
    }

    public ProposalResponseDto reject(int id, ProposalStatusDto dto) {
        Proposal proposal = getProposal(id);
        proposal.setProposalStatus(ProposalStatus.REJECTED);
        proposal.setRemarks(dto.remarks());
        proposalRepository.save(proposal);

        emailService.sendEmail(proposal.getCustomer().getEmail(), "Your proposal has been rejected. Reason: " + dto.remarks());
        return ProposalMapper.proposalToDto(proposal);
    }

    public ProposalResponseDto requestInfo(int id, ProposalStatusDto dto) {
        Proposal proposal = getProposal(id);
        proposal.setProposalStatus(ProposalStatus.INFO_REQUESTED);
        proposal.setRequestDetails(dto.requestDetails());
        proposalRepository.save(proposal);

        emailService.sendEmail(proposal.getCustomer().getEmail(), "Additional information requested: " + dto.requestDetails());
        return ProposalMapper.proposalToDto(proposal);
    }

    public ProposalDocumentDto uploadDocument(int id, DocumentType documentType, MultipartFile file, String username) {
        Proposal proposal = getProposal(id);
        checkCustomerOwnProposal(proposal, username);


        String fileName = file.getOriginalFilename();
        if (fileName == null ||
                !(fileName.endsWith(".pdf") || fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
            throw new RuntimeException("Only PDF, JPG and PNG files are allowed");
        }

        try {
            Path uploadPath = Path.of("uploads", "proposals", String.valueOf(id));
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(System.currentTimeMillis() + "_" + fileName);
            file.transferTo(filePath);

            ProposalDocument document = new ProposalDocument();
            document.setProposal(proposal);
            document.setDocumentType(documentType);
            document.setFileName(fileName);
            document.setFilePath(filePath.toString());
            proposalDocumentRepository.save(document);

            return ProposalMapper.documentToDto(document);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }

    public List<ProposalDocumentDto> getDocuments(int id, String username) {
        Proposal proposal = getProposal(id);
        checkProposalAccess(proposal, username);

        return proposalDocumentRepository.findByProposalId(id)
                .stream()
                .map(ProposalMapper::documentToDto)
                .toList();
    }

    private Proposal getProposal(int id) {
        return proposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Proposal Id"));
    }

    private void checkProposalAccess(Proposal proposal, String username) {
        User loginUser = (User) userService.loadUserByUsername(username);

        if (loginUser.getRole() == Role.CUSTOMER
                && proposal.getCustomer().getUser().getId() != loginUser.getId()) {
            throw new RuntimeException("You can see only your proposal");
        }
    }

    private void checkCustomerOwnProposal(Proposal proposal, String username) {
        User loginUser = (User) userService.loadUserByUsername(username);

        if (loginUser.getRole() != Role.CUSTOMER
                || proposal.getCustomer().getUser().getId() != loginUser.getId()) {
            throw new RuntimeException("You can upload document only for your proposal");
        }
    }
}
