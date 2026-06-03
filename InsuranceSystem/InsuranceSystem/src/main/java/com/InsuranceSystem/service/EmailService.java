package com.InsuranceSystem.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String email, String message) {
        System.out.println("Email sent to " + email + ": " + message);
    }
}
