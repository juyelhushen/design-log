package com.newsletter.service.impl;

import com.newsletter.dto.*;
import com.newsletter.entity.RefreshToken;
import com.newsletter.entity.User;
import com.newsletter.exception.custom.DuplicateUserException;
import com.newsletter.factory.UserFactory;
import com.newsletter.mapper.UserMapper;
import com.newsletter.repository.UserRepository;
import com.newsletter.security.JwtService;
import com.newsletter.security.RefreshTokenService;
import com.newsletter.security.payload.TokenPair;
import com.newsletter.security.user.UserPrincipal;
import com.newsletter.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserFactory userFactory;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateUserException("Email already exists: " + email);
        }

        if (StringUtils.hasText(request.username()) && userRepository.existsByUsername(request.username().trim().toLowerCase())) {
            throw new DuplicateUserException("Username already exists: " + request.username());
        }

        User user = User.builder()
                .email(email)
                .username(StringUtils.hasText(request.username()) ? request.username().trim().toLowerCase() : null)
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User saved = userRepository.save(user);
        TokenPair tokens = issueTokens(saved);
        return buildAuthResponse(saved, tokens);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email().trim().toLowerCase(),
                        request.password())
        );

        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();
        TokenPair tokens = issueTokens(user);
        return buildAuthResponse(user, tokens);
    }


    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.validateAndGet(request.refreshToken());
        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = refreshTokenService.issueToken(user).getTokenHash();

        refreshToken.setRevoked(true);
        TokenPair tokens = TokenPair.builder()
                .accessToken(newAccessToken)
                .expiresIn(900)
                .refreshToken(newRefreshToken)
                .build();
        return buildAuthResponse(user, tokens);
    }


    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }

    private TokenPair issueTokens(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.issueToken(user).getTokenHash();
        return TokenPair.builder()
                .accessToken(accessToken)
                .expiresIn(900)
                .refreshToken(refreshToken)
                .build();
    }


    private AuthResponse buildAuthResponse(User user, TokenPair tokens) {
        UserProfileResponse profile = UserMapper.toProfileResponse(user);
        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(tokens.accessToken())
                .expiresIn(tokens.expiresIn())
                .refreshToken(tokens.refreshToken())
                .user(profile)
                .build();
    }

}