package com.dayma.service;

public interface EmailService {
    void sendVerificationEmail(String to, String code);
    void sendNewsletter(String to);
}
