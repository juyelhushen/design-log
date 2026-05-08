package com.newsletter.service;

import com.newsletter.dto.AuthResponse;
import com.newsletter.dto.LoginRequest;
import com.newsletter.dto.RefreshTokenRequest;
import com.newsletter.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
    void logout(RefreshTokenRequest request);
}