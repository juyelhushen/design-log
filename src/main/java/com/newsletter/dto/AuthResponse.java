package com.newsletter.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String tokenType,
        String accessToken,
        long expiresIn,
        String refreshToken,
        UserProfileResponse user
) {
}
