package com.newsletter.security;

import com.newsletter.dto.AuthResponse;
import com.newsletter.entity.User;
import com.newsletter.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2UserProvisioningService provisioningService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        User user = provisioningService.upsertGoogleUser(oauth2User);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.issueToken(user).getTokenHash();

        AuthResponse body = AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .expiresIn(900)
                .refreshToken(refreshToken)
                .user(UserMapper.toProfileResponse(user))
                .build();

        String redirectUrl = String.format(
                "http://localhost:5173/oauth-success?accessToken=%s&refreshToken=%s",
                accessToken,
                refreshToken
        );

        response.sendRedirect(redirectUrl);
    }
}
