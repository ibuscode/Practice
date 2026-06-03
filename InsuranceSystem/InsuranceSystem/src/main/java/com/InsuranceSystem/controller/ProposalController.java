package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.ProposalDocumentDto;
import com.InsuranceSystem.dto.ProposalRequestDto;
import com.InsuranceSystem.dto.ProposalResponseDto;
import com.InsuranceSystem.dto.ProposalStatusDto;
import com.InsuranceSystem.enums.DocumentType;
import com.InsuranceSystem.enums.ProposalStatus;
import com.InsuranceSystem.service.ProposalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    @PostMapping("/api/v1/proposals")
    public ProposalResponseDto create(@Valid @RequestBody ProposalRequestDto dto, Principal principal) {
        return proposalService.create(dto, principal.getName());
    }

    @GetMapping("/api/v1/proposals/user/{userId}")
    public List<ProposalResponseDto> getByUser(@PathVariable int userId, Principal principal) {
        return proposalService.getByUser(userId, principal.getName());
    }

    @GetMapping("/api/v1/proposals/{id}")
    public ProposalResponseDto getById(@PathVariable int id, Principal principal) {
        return proposalService.getById(id, principal.getName());
    }

    @GetMapping("/api/v1/proposals")
    public Page<ProposalResponseDto> getAll(
            @RequestParam(required = false) ProposalStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return proposalService.getAll(status, dateFrom, dateTo, page, size);
    }

    @PutMapping("/api/v1/proposals/{id}/approve")
    public ProposalResponseDto approve(@PathVariable int id, Principal principal) {
        return proposalService.approve(id, principal.getName());
    }

    @PutMapping("/api/v1/proposals/{id}/reject")
    public ProposalResponseDto reject(@PathVariable int id, @RequestBody ProposalStatusDto dto) {
        return proposalService.reject(id, dto);
    }

    @PutMapping("/api/v1/proposals/{id}/request-info")
    public ProposalResponseDto requestInfo(@PathVariable int id, @RequestBody ProposalStatusDto dto) {
        return proposalService.requestInfo(id, dto);
    }

    @PostMapping("/api/v1/proposals/{id}/documents")
    public ProposalDocumentDto uploadDocument(
           @PathVariable int id,
            @RequestParam DocumentType documentType,
            @RequestParam MultipartFile file,
            Principal principal) {
        return proposalService.uploadDocument(id, documentType, file, principal.getName());
    }

    @GetMapping("/api/v1/proposals/{id}/documents")
    public List<ProposalDocumentDto> getDocuments(@PathVariable int id, Principal principal) {
        return proposalService.getDocuments(id, principal.getName());
    }
}
