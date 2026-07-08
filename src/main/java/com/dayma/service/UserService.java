package com.dayma.service;

import com.dayma.dto.AuthResponse;
import com.dayma.dto.GoogleLoginRequest;
import com.dayma.dto.LoginRequest;
import com.dayma.dto.RegisterRequest;
import com.dayma.model.User;

import java.time.LocalDateTime;

public interface UserService {
    User getCurrentUser();
    AuthResponse signup(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
    String verify(String code);
    AuthResponse googleLogin(GoogleLoginRequest request);
    AuthResponse googleSignup(GoogleLoginRequest request);
    void newsletterSubscribe();
    void newsletterUnsubscribe();
    String newsletterUnsubscribe(String email);
    long countByRegistrationDateBetween(LocalDateTime start, LocalDateTime end);
}
