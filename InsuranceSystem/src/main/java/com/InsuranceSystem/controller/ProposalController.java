package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.*;
import com.InsuranceSystem.service.OfficerService;
import com.InsuranceSystem.service.ProposalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/proposal")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class ProposalController {

    private final ProposalService proposalService;
    private final OfficerService officerService;

    @PostMapping("/rcbook/upload")
    public void uploadRcBook(Principal principal,
                             @RequestParam("quoteId") int quoteId,
                             @RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        proposalService.uploadRcBook(username, quoteId, file);
    }

    @PostMapping("/license/upload")
    public void uploadDrivingLicense(Principal principal,
                                     @RequestParam("quoteId") int quoteId,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        proposalService.uploadDrivingLicense(username, quoteId, file);
    }

   /* @PostMapping("/vehiclephoto/upload")
    public void uploadVehiclePhoto(Principal principal,
                                   @RequestParam("quoteId") int quoteId,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        proposalService.uploadVehiclePhoto(username, quoteId, file);
    }*/

    @PostMapping("/assign-officer")
    public ProposalListDto assignOfficer(@Valid @RequestBody AssignOfficerRequestDto request) {
        return proposalService.assignOfficer(request);
    }

    // Get all officers
    @GetMapping("/officers/get")
    public List<OfficerResponseDto> getAllOfficers() {
        return officerService.getAllOfficers();
    }

    @GetMapping("/submitted")
    public List<ProposalListDto> getSubmittedProposals() {
        return proposalService.getSubmittedProposals();
    }
    @GetMapping("/my-assigned-proposals")
    public List<AssignedProposalDto> getMyAssignedProposals(Principal principal) {
        String username = principal.getName();
        return proposalService.getAssignedProposalsForOfficer(username);
    }
    @GetMapping("/details/{proposalId}")
    public ProposalDetailsDto getProposalDetails(@PathVariable int proposalId) {
        return proposalService.getProposalDetails(proposalId);
    }

    @PutMapping("/approve/{proposalId}")
    public ProposalDetailsDto approveProposal(@PathVariable int proposalId) {
        return proposalService.updateProposalStatus(proposalId, "APPROVED");
    }

    @PutMapping("/reject/{proposalId}")
    public ProposalDetailsDto rejectProposal(@PathVariable int proposalId) {
        return proposalService.updateProposalStatus(proposalId, "REJECTED");
    }

}
