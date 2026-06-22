package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.ClaimRequestDto;
import com.InsuranceSystem.dto.ClaimResponseDto;
import com.InsuranceSystem.dto.ClaimReviewRequestDto;
import com.InsuranceSystem.enums.ClaimStatus;
import com.InsuranceSystem.enums.ClaimType;
import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.enums.PolicyStatus;
import com.InsuranceSystem.mapper.ClaimMapper;
import com.InsuranceSystem.model.*;
import com.InsuranceSystem.repository.*;
import com.InsuranceSystem.utility.FileUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;
    private final OfficerRepository officerRepository;
    private final GarageRepository garageRepository;

    private static final String UPLOAD_LOC = "E:/Trainning/insurance_UI/public/Documents";



    public ClaimResponseDto fileClaim(@Valid ClaimRequestDto request, String username) {
        // Step 1: Find customer
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Step 2: Find policy
        Policy policy = policyRepository.findById(request.policyId().intValue())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        // Step 3: Check if policy belongs to customer
        if (policy.getProposal().getCustomer().getCustomerId() != customer.getCustomerId()) {
            throw new RuntimeException("You can only file claims for your own policies");
        }

        // Step 4: Check if policy is ACTIVE
        if (policy.getPolicyStatus() != PolicyStatus.ACTIVE) {
            throw new RuntimeException("Policy is not active. Current status: " + policy.getPolicyStatus());
        }

        // Step 5: Check claim amount <= coverage amount
        double coverageAmount = policy.getProposal().getQuote().getCoverageAmount();
        if (request.claimAmount() > coverageAmount) {
            throw new RuntimeException("Claim amount exceeds coverage amount. Coverage: ₹" + coverageAmount);
        }

        // Step 6: Check for duplicate active claim
        boolean activeClaim = claimRepository.existsByPolicyAndClaimStatusIn(
                policy, List.of(ClaimStatus.SUBMITTED, ClaimStatus.UNDER_REVIEW, ClaimStatus.APPROVED )
        );
        if (activeClaim) {
            throw new RuntimeException("An active claim already exists for this policy");
        }

        Garage garage = null;
        if (request.claimType() == ClaimType.CASHLESS) {
            if (request.garageId() == null) {
                throw new RuntimeException("Garage is required for CASHLESS claims");
            }

             garage = garageRepository.findById(request.garageId())
                    .orElseThrow(() -> new RuntimeException("Garage not found"));

            if (garage.getStatus() != GarageStatus.ACTIVE) {
                throw new RuntimeException("Garage is not active. Please select an active garage");
            }
        }

        // Step 8: REIMBURSEMENT validation
        if (request.claimType() == ClaimType.REIMBURSEMENT) {
            if (request.repairBillAmount() == null) {
                throw new RuntimeException("Repair bill amount is required for REIMBURSEMENT claims");
            }
        }

        // Step 9: Create claim
        Claim claim = ClaimMapper.toEntity(request, policy, customer, garage);
        Claim savedClaim = claimRepository.save(claim);

        return ClaimMapper.toDto(savedClaim);
    }

    public List<ClaimResponseDto> getMyClaims(String username) {
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Claim> claims = claimRepository.findByCustomerCustomerId(customer.getCustomerId());

        return claims.stream()
                .map(ClaimMapper::toDto)
                .toList();
    }
    public Page<ClaimResponseDto> getClaimsForOfficer(String username, int page, int size) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Claim> claimsPage = claimRepository.findClaimsByOfficerId(officer.getOfficerId(), pageable);

        return claimsPage.map(ClaimMapper::toDto);
    }

    public Page<ClaimResponseDto> getSubmittedClaimsForOfficer(String username, int page, int size) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Claim> claimsPage = claimRepository.findClaimsByOfficerIdAndStatus(
                officer.getOfficerId(),
                ClaimStatus.SUBMITTED,
                pageable
        );

        return claimsPage.map(ClaimMapper::toDto);
    }
    // Officer: Get all claims
    public List<ClaimResponseDto> getAllClaims() {
        List<Claim> claims = claimRepository.findAll();

        return claims.stream()
                .map(ClaimMapper::toDto)
                .toList();
    }


    // Get single claim details (with authorization check)
    public ClaimResponseDto getClaimDetails(int claimId, String username) {

        // Step 1: Get officer
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        // Step 2: Get claim with details
        Claim claim = claimRepository.findClaimWithDetails(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // Step 3: Check if proposal is assigned to this officer
        if (claim.getPolicy().getProposal().getOfficer() == null ||
                claim.getPolicy().getProposal().getOfficer().getOfficerId() != officer.getOfficerId()) {
            throw new RuntimeException("You are not authorized to view this claim");
        }

        // Step 4: Return DTO
        return ClaimMapper.toDto(claim);
    }
    public ClaimResponseDto reviewClaim(int claimId, ClaimReviewRequestDto request, String username) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setClaimStatus(ClaimStatus.UNDER_REVIEW);
        claim.setOfficer(officer);
        claim.setOfficerRemarks(request.officerRemarks());

        Claim updatedClaim = claimRepository.save(claim);
        return ClaimMapper.toDto(updatedClaim);
    }
    public ClaimResponseDto approveClaim(int claimId, ClaimReviewRequestDto request, String username) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setClaimStatus(ClaimStatus.APPROVED);
        claim.setOfficer(officer);
        claim.setOfficerRemarks(request.officerRemarks());

        Claim updatedClaim = claimRepository.save(claim);
        return ClaimMapper.toDto(updatedClaim);
    }

    public ClaimResponseDto rejectClaim(int claimId, ClaimReviewRequestDto request, String username) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setClaimStatus(ClaimStatus.REJECTED);
        claim.setOfficer(officer);
        claim.setOfficerRemarks(request.officerRemarks());

        Claim updatedClaim = claimRepository.save(claim);
        return ClaimMapper.toDto(updatedClaim);
    }
    public void uploadReceipt(String username, int claimId, MultipartFile file) throws IOException {

        // Step 1: Find customer
        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Step 2: Find claim (with ownership validation)
        Claim claim = claimRepository.findByClaimIdAndCustomerCustomerId(claimId, customer.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Claim not found for this customer"));

        // Step 3: Validate file
        FileUtility.validateFile(file);

        // Step 4: Generate filename
        String fileName = "receipt_" + claimId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Step 5: Create upload path
        Path uploadPath = Paths.get(UPLOAD_LOC);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Step 6: Destination path
        Path destinationPath = uploadPath.resolve(fileName);

        // Step 7: Copy file
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Step 8: Save to database
        claim.setReceiptPath(fileName);

        // Step 9: If claim type is REIMBURSEMENT, update status if needed
        if (claim.getClaimType() == ClaimType.REIMBURSEMENT) {
            if (claim.getClaimStatus() == ClaimStatus.SUBMITTED) {
                claim.setClaimStatus(ClaimStatus.UNDER_REVIEW);
            }
        }

        claimRepository.save(claim);
    }

}
