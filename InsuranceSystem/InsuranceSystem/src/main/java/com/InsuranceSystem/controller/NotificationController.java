package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.QuoteEmailResponseDto;
import com.InsuranceSystem.dto.SendQuoteRequestDto;
import com.InsuranceSystem.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("api/notificaions/send-quote")
    public ResponseEntity<QuoteEmailResponseDto> sendQuoteEmail(@Valid @RequestBody SendQuoteRequestDto request) {
        QuoteEmailResponseDto response = notificationService.sendQuoteEmail(request.quoteId());
        return ResponseEntity.ok(response);
    }

}
