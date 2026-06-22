package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.ClaimRequestDto;
import com.InsuranceSystem.dto.ClaimResponseDto;
import com.InsuranceSystem.dto.ClaimReviewRequestDto;
import com.InsuranceSystem.service.ClaimService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class ClaimController {

    private final ClaimService claimService;

    // Customer: File a claim
    @PostMapping("/file")
    public ClaimResponseDto fileClaim(@Valid @RequestBody ClaimRequestDto request, Principal principal) {
        String username = principal.getName();
        return claimService.fileClaim(request, username);
    }

    // Customer: View my claims
    @GetMapping("/my-claims")
    public List<ClaimResponseDto> getMyClaims(Principal principal) {
        String username = principal.getName();
        return claimService.getMyClaims(username);
    }

    // Officer: View all claims
    @GetMapping("/all")
    public List<ClaimResponseDto> getAllClaims() {
        return claimService.getAllClaims();
    }

    @GetMapping("/assigned")
    public Page<ClaimResponseDto> getAssignedClaims(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        String username = principal.getName();
        return claimService.getClaimsForOfficer(username, page, size);
    }

    @GetMapping("/assigned/submitted")
    public Page<ClaimResponseDto> getSubmittedAssignedClaims(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        String username = principal.getName();
        return claimService.getSubmittedClaimsForOfficer(username, page, size);
    }

    @GetMapping("/details/{claimId}")
    public ClaimResponseDto getClaimDetails(@PathVariable int claimId, Principal principal) {
        String username = principal.getName();
        return claimService.getClaimDetails(claimId, username);
    }

    // Officer: Review claim (mark as UNDER_REVIEW)
    @PutMapping("/{claimId}/review")
    public ClaimResponseDto reviewClaim(@PathVariable int claimId,
                                        @RequestBody ClaimReviewRequestDto request,
                                        Principal principal) {
        String username = principal.getName();
        return claimService.reviewClaim(claimId, request, username);
    }

    // Officer: Approve claim
    @PutMapping("/{claimId}/approve")
    public ClaimResponseDto approveClaim(@PathVariable int claimId,
                                         @RequestBody ClaimReviewRequestDto request,
                                         Principal principal) {
        String username = principal.getName();
        return claimService.approveClaim(claimId, request, username);
    }

    // Officer: Reject claim
    @PutMapping("/{claimId}/reject")
    public ClaimResponseDto rejectClaim(@PathVariable int claimId,
                                        @RequestBody ClaimReviewRequestDto request,
                                        Principal principal) {
        String username = principal.getName();
        return claimService.rejectClaim(claimId, request, username);
    }

    @PostMapping("/receipt/upload")
    public void uploadReceipt(Principal principal,
                              @RequestParam("claimId") int claimId,
                              @RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        claimService.uploadReceipt(username, claimId, file);
    }
}
