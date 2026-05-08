package com.newsletter.security.payload;

import lombok.Builder;

@Builder
public record TokenPair(
        String accessToken,
        long expiresIn,
        String refreshToken
) {
}
